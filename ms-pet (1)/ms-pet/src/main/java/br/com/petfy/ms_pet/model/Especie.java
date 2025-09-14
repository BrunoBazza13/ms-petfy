package br.com.petfy.ms_pet.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "especie")
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;

    @OneToMany(mappedBy = "especie", cascade = CascadeType.PERSIST)  // uma especie possui varias raças
    private List<Raca> racas = new ArrayList<>();

    public List<Raca> getRacas() {
        return racas;
    }

    public void setRacas(List<Raca> racas) {
        this.racas = racas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

