package br.com.petfy.ms_pet.repository;

import br.com.petfy.ms_pet.model.Especie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EspecieRepository extends JpaRepository<Especie, UUID> {
}
