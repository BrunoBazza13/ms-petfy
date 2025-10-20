package br.com.petfy.ms_pet.repository;

import br.com.petfy.ms_pet.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TutorRepository extends JpaRepository<Tutor, UUID> {

    Optional<Tutor> findByUsuarioId(UUID usuarioId);


}
