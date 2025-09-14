package br.com.petfy.ms_authentication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tutor")
public class Tutor extends User{

    public Tutor() {
        super();
    }

    public Tutor(String login, String encryptedPassword, UserRole role) {
        super(login, encryptedPassword, role);
    }
}
