package br.com.petfy.ms_procimento.repository;

import br.com.petfy.ms_procimento.model.Vacinas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VacinasRespository extends JpaRepository<Vacinas, UUID> {
}
