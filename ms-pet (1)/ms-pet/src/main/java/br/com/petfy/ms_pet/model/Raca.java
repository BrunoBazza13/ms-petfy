package br.com.petfy.ms_pet.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "racas")
public class Raca {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;

    @ManyToOne  // varias raças pertence a uma unica especie
    @JoinColumn(name = "especie_id") // cria a chave estrangeira
    private Especie especie;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }
}
