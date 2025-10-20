package br.com.petfy.ms_procimento.repository;

import br.com.petfy.ms_procimento.model.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProntuarioRepository extends JpaRepository<Prontuario, UUID> {

    List<Prontuario> findByPetId(UUID petId);

}
