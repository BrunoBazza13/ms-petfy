package br.com.petfy.ms_procimento.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("VACINA")
public class GuiaVacina extends Guia{

    @ManyToOne
    @JoinColumn(name = "vacinas_id")
    private Vacinas vacina;

    public Vacinas getVacina() {
        return vacina;
    }

    public void setVacina(Vacinas vacina) {
        this.vacina = vacina;
    }

    @Override
    public String getNomeProcedimento() {
        return this.vacina.getNome();
    }

    @Override
    public String getTipo() {
        return "VACINA";
    }

}
