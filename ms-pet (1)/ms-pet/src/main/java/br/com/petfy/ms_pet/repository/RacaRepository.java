package br.com.petfy.ms_pet.repository;

import br.com.petfy.ms_pet.model.Raca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RacaRepository extends JpaRepository<Raca, UUID> {
}
