package br.com.petfy.ms_procimento.repository;

import br.com.petfy.ms_procimento.model.Exames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExamesRespository extends JpaRepository<Exames, UUID> {
}
