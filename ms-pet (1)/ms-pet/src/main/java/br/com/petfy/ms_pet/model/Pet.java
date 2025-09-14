package br.com.petfy.ms_pet.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String porte;
    private int idade;
    private char sexo;
    private double peso;

    @ManyToOne(cascade = CascadeType.ALL)     // varios pets pertence a uma unica raça
    @JoinColumn(name = "raca_id")
    private Raca raca;

    private int tutorID;

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getTutorID() {
        return tutorID;
    }

    public void setTutorID(int tutorID) {
        this.tutorID = tutorID;
    }

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

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }
}

