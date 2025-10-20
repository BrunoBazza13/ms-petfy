package br.com.petfy.ms_clinica.repository;

import br.com.petfy.ms_clinica.model.Agenda;
import br.com.petfy.ms_clinica.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AgendaRepository extends JpaRepository<Agenda, UUID> {

    List <Agenda> findByVeterinarioIdAndStatusAndDataHoraInicioAfterOrderByDataHoraInicioAsc(
            UUID veterinarioId,
            Status status,
            LocalDateTime dataAtual
    );

}
