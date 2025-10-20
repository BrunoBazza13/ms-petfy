package br.com.petfy.ms_clinica.service;

import br.com.petfy.ms_clinica.dto.agenda.AgendaDetalhadaDto;
import br.com.petfy.ms_clinica.dto.agenda.AgendaRequestDTO;
import br.com.petfy.ms_clinica.dto.agenda.AgendaResponseDTO;
import br.com.petfy.ms_clinica.dto.clinica.ClinicaResponseDto;
import br.com.petfy.ms_clinica.model.Agenda;
import br.com.petfy.ms_clinica.model.Status;
import br.com.petfy.ms_clinica.model.Veterinario;
import br.com.petfy.ms_clinica.repository.AgendaRepository;
import br.com.petfy.ms_clinica.repository.VeterinarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Transactional
    public void gerarHorarios(AgendaRequestDTO request) {
        // 1. Busca o veterinário para associar aos horários
        Veterinario veterinario = veterinarioRepository.findById(request.veterinario())
                .orElseThrow(() -> new RuntimeException("Veterinário não encontrado."));

        List<Agenda> novosHorarios = new ArrayList<>();

        // 2. Loop para cada data fornecida na requisição
        for (LocalDate data : request.datas()) {
            LocalDateTime horarioAtual = data.atTime(request.horaInicio());
            LocalDateTime horarioFinal = data.atTime(request.horaFim());

            // 3. Loop que cria os "slots" de tempo para o dia
            while (horarioAtual.isBefore(horarioFinal)) {
                Agenda novoHorario = new Agenda();
                novoHorario.setVeterinario(veterinario);
                novoHorario.setDataHoraInicio(horarioAtual);

                LocalDateTime horarioFimSlot = horarioAtual.plusMinutes(request.duracaoConsultaEmMinutos());
                novoHorario.setDataHoraFim(horarioFimSlot);

                novoHorario.setStatus(Status.DISPONIVEL);

                novosHorarios.add(novoHorario);

                // Avança para o próximo slot
                horarioAtual = horarioFimSlot;
            }
        }

        // 4. Salva todos os novos horários no banco de uma só vez
        agendaRepository.saveAll(novosHorarios);
    }

    public List<AgendaResponseDTO> buscarHorariosDisponiveis(UUID veterinarioId) {

        try {
            List<Agenda> horarios = agendaRepository.findByVeterinarioIdAndStatusAndDataHoraInicioAfterOrderByDataHoraInicioAsc(
                    veterinarioId,
                    Status.DISPONIVEL,
                    LocalDateTime.now()
            );

            return horarios.stream()
                    .map(AgendaResponseDTO::new)
                    .toList();

        } catch (Exception e) {
            e.printStackTrace(); // loga no console
            throw e; // deixa a exception subir para ver o stacktrace no log
        }

    }

    @Transactional
    public void ocuparHorario(UUID agendaId) {

        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new RuntimeException("Horário com ID " + agendaId + " não encontrado."));

        if (agenda.getStatus() != Status.DISPONIVEL) {
            throw new IllegalStateException("Este horário não está mais disponível para agendamento.");
        }

        agenda.setStatus(Status.AGENDADO);

        agendaRepository.save(agenda);
    }



    public List<AgendaDetalhadaDto> listarAgendamentosComDetalhes(List<UUID> ids) {
        List<Agenda> agendas = agendaRepository.findAllById(ids);
        return agendas.stream()
                .map(AgendaDetalhadaDto::new)
                .toList();
    }


}
