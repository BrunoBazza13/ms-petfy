//package br.com.petfy.ms_authentication.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "veterinario")
//public class Veterinario extends User{
//
//    private String crmv;
//    private String nome;
//    private String uf;
//
//    public Veterinario(){
//    super();
//    }
//
//    public Veterinario(String login, String encryptedPassword, UserRole userRole, String nome, String crmv, String uf) {
//        super(login, encryptedPassword, userRole);
//        this.nome = nome;
//        this.crmv = crmv;
//        this.uf = uf;
//    }
//
//    public String getCrmv() {
//        return crmv;
//    }
//
//    public void setCrmv(String crmv) {
//        this.crmv = crmv;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public String getUf() {
//        return uf;
//    }
//
//    public void setUf(String uf) {
//        this.uf = uf;
//    }
//}
