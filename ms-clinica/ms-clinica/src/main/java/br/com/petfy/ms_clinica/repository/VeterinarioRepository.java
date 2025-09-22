package br.com.petfy.ms_clinica.repository;

import br.com.petfy.ms_clinica.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VeterinarioRepository extends JpaRepository <Veterinario, UUID> {
}
