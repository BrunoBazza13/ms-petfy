package br.com.petfy.ms_clinica.model;


import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "veterinario")
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String crmv;
    private String uf;
    @Column(unique = true)
    private UUID usuarioId; // O link para o usuário no ms-auth

    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agenda> agenda = new ArrayList<>();

    @ManyToMany(mappedBy = "veterinarios")
    private Set<Clinica> clinicas = new HashSet<>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Agenda> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<Agenda> agenda) {
        this.agenda = agenda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Set<Clinica> getClinicas() {
        return clinicas;
    }

    public void setClinicas(Set<Clinica> clinicas) {
        this.clinicas = clinicas;
    }
}
