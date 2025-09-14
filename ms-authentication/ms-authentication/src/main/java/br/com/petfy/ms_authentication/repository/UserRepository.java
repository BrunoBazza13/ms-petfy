package br.com.petfy.ms_authentication.repository;

import br.com.petfy.ms_authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByLogin(String login);

}
