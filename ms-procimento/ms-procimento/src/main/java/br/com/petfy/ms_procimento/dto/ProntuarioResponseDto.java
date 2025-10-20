package br.com.petfy.ms_procimento.dto;

import br.com.petfy.ms_procimento.model.Prontuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProntuarioResponseDto(   UUID id,
                                       LocalDateTime dataConsulta,
                                       String sinaisClinicos,
                                       String exameFisico,
                                       String diagnostico,
                                       String condutasRealizadas,
                                       String evolucaoClinica,
                                       String prescricao,

                                       // 👇 Dados agregados vindos de outros microsserviços
                                       UUID agendamentoId,
                                       PetResponseDto pet,
                                       ClinicaResponseDto clinica,
                                       VeterinarioResponseDto veterinario,

                                       // 👇 Guias (exames/vacinas) geradas
                                       List<GuiaResponseDto> guias
) {

    public ProntuarioResponseDto(
            Prontuario prontuario,
            PetResponseDto pet,
            ClinicaResponseDto clinica,
            VeterinarioResponseDto veterinario
    ) {
        this(
                prontuario.getId(),
                prontuario.getDataConsulta(),
                prontuario.getSinaisClinicos(),
                prontuario.getExameFisico(),
                prontuario.getDiagnostico(),
                prontuario.getCondutasRealizadas(),
                prontuario.getEvolucaoClinica(),
                prontuario.getPrescricao(),
                prontuario.getAgendamentoId(),
                pet,
                clinica,
                veterinario,
                prontuario.getGuiasGeradas().stream()
                        .map(guia -> new GuiaResponseDto(
                                guia.getId(),
                                guia.getTipo(),
                                guia.getNomeProcedimento(),
                                guia.getStatus()
                        ))
                        .collect(Collectors.toList())
        );
    }



}
