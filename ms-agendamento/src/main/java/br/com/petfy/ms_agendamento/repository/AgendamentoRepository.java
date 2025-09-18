package br.com.petfy.ms_agendamento.repository;

import br.com.petfy.ms_agendamento.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
}
