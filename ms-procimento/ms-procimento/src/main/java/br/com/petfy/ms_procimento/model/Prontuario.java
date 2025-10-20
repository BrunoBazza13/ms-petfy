package br.com.petfy.ms_procimento.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prontuarios")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID veterinarioId;
    private UUID petId;
    private UUID clinicaId;
    private UUID agendamentoId;

    private LocalDateTime dataConsulta;
    private String sinaisClinicos;
    private String exameFisico;
    private String diagnostico;
    private String condutasRealizadas;
    private String evolucaoClinica;
    private String prescricao;
    private String procedimentosSolicitados;

    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL)
    private List<Guia> guiasGeradas = new ArrayList<>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(UUID veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public UUID getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(UUID clinicaId) {
        this.clinicaId = clinicaId;
    }

    public UUID getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(UUID agendamentoId) {
        this.agendamentoId = agendamentoId;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getSinaisClinicos() {
        return sinaisClinicos;
    }

    public void setSinaisClinicos(String sinaisClinicos) {
        this.sinaisClinicos = sinaisClinicos;
    }

    public String getExameFisico() {
        return exameFisico;
    }

    public void setExameFisico(String exameFisico) {
        this.exameFisico = exameFisico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getCondutasRealizadas() {
        return condutasRealizadas;
    }

    public void setCondutasRealizadas(String condutasRealizadas) {
        this.condutasRealizadas = condutasRealizadas;
    }

    public String getEvolucaoClinica() {
        return evolucaoClinica;
    }

    public void setEvolucaoClinica(String evolucaoClinica) {
        this.evolucaoClinica = evolucaoClinica;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public String getProcedimentosSolicitados() {
        return procedimentosSolicitados;
    }

    public void setProcedimentosSolicitados(String procedimentosSolicitados) {
        this.procedimentosSolicitados = procedimentosSolicitados;
    }

    public List<Guia> getGuiasGeradas() {
        return guiasGeradas;
    }

    public void setGuiasGeradas(List<Guia> guiasGeradas) {
        this.guiasGeradas = guiasGeradas;
    }
}
