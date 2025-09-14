package br.com.petfy.ms_pet.repository;

import br.com.petfy.ms_pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {
}
