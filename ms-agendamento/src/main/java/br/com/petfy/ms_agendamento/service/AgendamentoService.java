package br.com.petfy.ms_agendamento.service;

import br.com.petfy.ms_agendamento.clint.ClinicaClint;
import br.com.petfy.ms_agendamento.clint.PetClint;
import br.com.petfy.ms_agendamento.dto.AgendamentoDetalhadoDto;
import br.com.petfy.ms_agendamento.dto.AgendamentoIdResponseDto;
import br.com.petfy.ms_agendamento.dto.AgendamentoResponseDto;
import br.com.petfy.ms_agendamento.dto.AgendamentoResquestDto;
import br.com.petfy.ms_agendamento.model.Agendamento;
import br.com.petfy.ms_agendamento.model.StatusAgendamento;
import br.com.petfy.ms_agendamento.repository.AgendamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private PetClint petClient;
    @Autowired
    private ClinicaClint clinicaClient;


    @Transactional
    public AgendamentoResponseDto criarAgendamento(AgendamentoResquestDto agendamentoResquestDto, UUID tutorId) {

        try {

            clinicaClient.agendarHorario(agendamentoResquestDto.agendaId());
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível agendar o horário. Causa: " + e.getMessage());
        }


        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setAgendaId(agendamentoResquestDto.agendaId());
        novoAgendamento.setPetId(agendamentoResquestDto.petId());
        novoAgendamento.setVeterinarioId(agendamentoResquestDto.veterinarioId());
        novoAgendamento.setClinicaId(agendamentoResquestDto.clinicaId());
        novoAgendamento.setMotivo(agendamentoResquestDto.motivo());
        novoAgendamento.setTutorId(tutorId);
        novoAgendamento.setStatus(StatusAgendamento.CONFIRMADO);


        Agendamento agendamentoSalvo = agendamentoRepository.save(novoAgendamento);

        return new AgendamentoResponseDto(agendamentoSalvo);
    }


    public List<AgendamentoDetalhadoDto> listaAgendamentos(UUID tutorId) {
        List<Agendamento> agendamentos = agendamentoRepository.findByTutorId(tutorId);

        if (agendamentos.isEmpty()) {
            return Collections.emptyList();
        }

        List<UUID> petIds = agendamentos.stream()
                .map(Agendamento::getPetId)
                .distinct()
                .toList();

// 3. Faz uma única chamada em lote para o ms-pet para buscar todos os pets de uma vez.
// Isso é muito mais eficiente do que fazer uma chamada para cada agendamento.
        List<PetClint.PetDTO> petsEncontrados = petClient.findPetsByIds(petIds);

// 4. Converte a lista de pets em um Mapa para facilitar a busca (Map<IdDoPet, ObjetoPet>).
// Isso nos permite encontrar os detalhes de um pet instantaneamente pelo seu ID.
        Map<UUID, PetClint.PetDTO> petsMap = petsEncontrados.stream()
                .collect(Collectors.toMap(PetClint.PetDTO::id, pet -> pet));

        List<UUID> agendaIds = agendamentos.stream().map(Agendamento::getAgendaId).distinct().toList();

        // A chamada ao Feign agora retorna uma lista do nosso DTO "plano"
        Map<UUID, ClinicaClint.AgendaDetalhadaDTO> agendasMap = clinicaClient.findAgendasByIds(agendaIds).stream()
                .collect(Collectors.toMap(ClinicaClint.AgendaDetalhadaDTO::id, agenda -> agenda));

        // --- LÓGICA DE MONTAGEM CORRIGIDA ---
        return agendamentos.stream().map(agendamento -> {
            PetClint.PetDTO pet = petsMap.get(agendamento.getPetId());
            ClinicaClint.AgendaDetalhadaDTO agendaDetalhada = agendasMap.get(agendamento.getAgendaId());

            // Tratamento para o caso de algum detalhe não ser encontrado
            if (agendaDetalhada == null) {
                return new AgendamentoDetalhadoDto(agendamento.getId(), agendamento.getStatus(), null,
                        pet != null ? pet.nome() : "Pet Removido", "Detalhes Indisponíveis", "Clínica Indisponível");
            }

            return new AgendamentoDetalhadoDto(
                    agendamento.getId(),
                    agendamento.getStatus(),
                    agendaDetalhada.dataHoraInicio(),
                    pet != null ? pet.nome() : "Pet Removido",
                    // Acede diretamente ao veterinário
                    agendaDetalhada.veterinario() != null ? agendaDetalhada.veterinario().nome() : "Veterinário Indisponível",
                    // Acede diretamente à clínica (NÃO está mais dentro do veterinário)
                    agendaDetalhada.clinica() != null ? agendaDetalhada.clinica().nome() : "Clínica Indisponível"
            );
        }).collect(Collectors.toList());
    }

    public AgendamentoIdResponseDto buscaAgendamentoPorID(UUID id){

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("agendamento não encontrada com o ID: " + id));

        System.out.print(agendamento.getPetId());
        return new AgendamentoIdResponseDto(agendamento);

    }


}
