package br.com.petfy.ms_procimento.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("EXAME")
public class GuiaExame extends Guia{

    @ManyToOne
    @JoinColumn(name = "exame_id")
    private Exames exame;

    public Exames getExame() {
        return exame;
    }

    public String getNome(){
        return exame.getNome();
    }

    public void setExame(Exames exame) {
        this.exame = exame;
    }

    @Override
    public String getNomeProcedimento() {
        return this.exame.getNome();
    }

    @Override
    public String getTipo() {
        return "EXAME";
    }

}
