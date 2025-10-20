package br.com.petfy.ms_procimento.repository;

import br.com.petfy.ms_procimento.model.GuiaExame;
import br.com.petfy.ms_procimento.model.GuiaVacina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuiaExameRepository extends JpaRepository<GuiaExame, UUID> {
}
