package br.com.petfy.ms_procimento.service;

import br.com.petfy.ms_procimento.client.AgendamentoClient;
import br.com.petfy.ms_procimento.client.ClinicaClint;
import br.com.petfy.ms_procimento.client.PetClient;
import br.com.petfy.ms_procimento.dto.*;
import br.com.petfy.ms_procimento.model.*;
import br.com.petfy.ms_procimento.repository.ExamesRespository;
import br.com.petfy.ms_procimento.repository.GuiaExameRepository;
import br.com.petfy.ms_procimento.repository.ProntuarioRepository;
import br.com.petfy.ms_procimento.repository.VacinasRespository;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProntuarioService {

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private AgendamentoClient agendamentoClient;

    @Autowired
    private PetClient petClient;

    @Autowired
    private VacinasRespository vacinasRespository;

    @Autowired
    private ExamesRespository examesRespository;

    @Autowired
    private ClinicaClint clinicaClient;


    @Transactional
    public ProntuarioResponseDto gerarProntuarioEGuias(ProntuarioRequestDto dto, UUID veterinarioUsuarioId) {

        AgendamentoClient.AgendamentoDTO agendamento;
        try {
            agendamento = agendamentoClient.findAgendamentoById(dto.agendamentoId()).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Agendamento com ID " + dto.agendamentoId() + " não encontrado ou serviço indisponível.");
        }

        Prontuario novoProntuario = new Prontuario();

        novoProntuario.setAgendamentoId(agendamento.id());
        novoProntuario.setPetId(agendamento.petId());
        novoProntuario.setVeterinarioId(agendamento.veterinarioId());
        novoProntuario.setClinicaId(agendamento.clinicaId());

        VeterinarioResponseDto veterinario = clinicaClient.buscarVeterinarioPorId(agendamento.veterinarioId());
        novoProntuario.setVeterinarioId(veterinario.id());

        novoProntuario.setSinaisClinicos(dto.sinaisClinicos());
        novoProntuario.setExameFisico(dto.exameFisico());
        novoProntuario.setDiagnostico(dto.diagnostico());
        novoProntuario.setCondutasRealizadas(dto.condutasRealizadas());
        novoProntuario.setEvolucaoClinica(dto.evolucaoClinica());
        novoProntuario.setDataConsulta(LocalDateTime.now());
        novoProntuario.setPrescricao(dto.prescricao());

        List<Guia> guiasGeradas = new ArrayList<>();

        if (dto.vacinasSolicitadasIds() != null && !dto.vacinasSolicitadasIds().isEmpty()) {
            for (UUID vacinaId : dto.vacinasSolicitadasIds()) {
                // Busca a vacina no catálogo
                Vacinas vacina = vacinasRespository.findById(vacinaId)
                        .orElseThrow(() -> new RuntimeException("Vacina com ID " + vacinaId + " não encontrada no catálogo."));

                // Cria a guia específica para esta vacina
                GuiaVacina guiaVacina = new GuiaVacina();
                guiaVacina.setProntuario(novoProntuario); // Vincula a guia ao prontuário
                guiaVacina.setVacina(vacina);
                guiaVacina.setStatus(StatusGuia.PENDENTE);
                guiasGeradas.add(guiaVacina);
            }
        }

        if (dto.examesSolicitadosIds() != null && !dto.examesSolicitadosIds().isEmpty()) {

            for (UUID exameId : dto.examesSolicitadosIds()) {

                Exames tipoExame = examesRespository.findById(exameId)
                        .orElseThrow(() -> new RuntimeException("Tipo de Exame com ID " + exameId + " não encontrado no catálogo."));

                GuiaExame guiaExame = new GuiaExame();
                guiaExame.setProntuario(novoProntuario);
                guiaExame.setExame(tipoExame);
                guiaExame.setStatus(StatusGuia.PENDENTE);
                guiasGeradas.add(guiaExame);
            }
        }

        novoProntuario.setGuiasGeradas(guiasGeradas);

        PetResponseDto pet = petClient.buscarPetPorId(agendamento.petId()).getBody();
        ClinicaResponseDto clinica = clinicaClient.bucarClinicaPorid(agendamento.clinicaId());


        Prontuario prontuarioSalvo = prontuarioRepository.save(novoProntuario);

        return new ProntuarioResponseDto(prontuarioSalvo, pet, clinica, veterinario);
    }


    public List<ProntuarioResponseDto> buscarProntuario(UUID petId){



        List<Prontuario> prontuarios = prontuarioRepository.findByPetId(petId);

        if (prontuarios.isEmpty()) {
            return Collections.emptyList();
        }

        // --- ETAPA 2: COLETAR IDs PARA AS BUSCAS EXTERNAS ---
        // Extrai os IDs únicos de veterinários e clínicas para evitar chamadas repetidas.
        List<UUID> veterinarioIds = prontuarios.stream().map(Prontuario::getVeterinarioId).distinct().toList();
        List<UUID> clinicaIds = prontuarios.stream().map(Prontuario::getClinicaId).distinct().toList();

        // --- ETAPA 3: ORQUESTRAR AS CHAMADAS EM LOTE ---
        // Faz uma única chamada para cada microserviço para obter todos os dados necessários de uma vez.
        PetResponseDto petInfo = petClient.buscarPetPorId(petId).getBody();

        Map<UUID, VeterinarioResponseDto> veterinariosMap = clinicaClient.findVeterinariosByIds(veterinarioIds).stream()
                .collect(Collectors.toMap(VeterinarioResponseDto::id, vet -> vet));

        Map<UUID, ClinicaResponseDto> clinicasMap = clinicaClient.findClinicasByIds(clinicaIds).stream()
                .collect(Collectors.toMap(ClinicaResponseDto::id, clinica -> clinica));

        // --- ETAPA 4: MONTAR A RESPOSTA FINAL ---
        // Itera sobre os prontuários encontrados e usa os Mapas para juntar as informações rapidamente.
        return prontuarios.stream().map(prontuario -> {
            VeterinarioResponseDto vetInfo = veterinariosMap.get(prontuario.getVeterinarioId());
            ClinicaResponseDto clinicaInfo = clinicasMap.get(prontuario.getClinicaId());


            // Converte as guias da entidade para DTOs
            List<GuiaResponseDto> guiasDto = prontuario.getGuiasGeradas().stream()
                    .map(guia -> new GuiaResponseDto(guia.getId(), guia.getTipo(), guia.getNomeProcedimento(), guia.getStatus()))
                    .toList();

            return new ProntuarioResponseDto(
                    prontuario,
                    petInfo,
                    clinicaInfo,
                    vetInfo
            );
        }).collect(Collectors.toList());
    }
        
}


