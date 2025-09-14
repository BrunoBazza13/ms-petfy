package br.com.petfy.ms_clinica.repository;

import br.com.petfy.ms_clinica.model.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClinicaRepository extends JpaRepository<Clinica, UUID> {

}
