package br.com.petfy.ms_agendamento.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String motivo;
    private UUID clinicaId;
    private UUID veterinarioId;
    private UUID petId;
    private UUID agendaId;
    private UUID tutorId;
    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    public UUID getTutorId() {
        return tutorId;
    }

    public void setTutorId(UUID tutorId) {
        this.tutorId = tutorId;
    }

    public UUID getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(UUID clinicaId) {
        this.clinicaId = clinicaId;
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

    public UUID getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(UUID agendaId) {
        this.agendaId = agendaId;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
