package br.com.petfy.ms_agendamento.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String motivo;
    private UUID clinicaId;
    private UUID veterinarioId;
    private UUID petId;
    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;


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
