package br.com.petfy.ms_authentication.model;

public enum UserRole {

    ADMIN("admin"),

    USER("user"),

    TUTOR("tutor");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
