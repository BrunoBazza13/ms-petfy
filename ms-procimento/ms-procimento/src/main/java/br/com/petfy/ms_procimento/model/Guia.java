package br.com.petfy.ms_procimento.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "guias")
// Estratégia de herança: uma única tabela para todas as guias
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Coluna que vai diferenciar se é 'VACINA' ou 'EXAME'
@DiscriminatorColumn(name = "tipo_guia", discriminatorType = DiscriminatorType.STRING)
public abstract class Guia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;
    private LocalDate localDate;

   @Enumerated(EnumType.STRING)
    private StatusGuia status;

    public abstract String getTipo();

    public abstract String getNomeProcedimento();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public StatusGuia getStatus() {
        return status;
    }

    public void setStatus(StatusGuia status) {
        this.status = status;
    }
}
