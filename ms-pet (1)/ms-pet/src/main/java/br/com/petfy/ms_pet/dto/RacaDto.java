package br.com.petfy.ms_pet.dto;

import br.com.petfy.ms_pet.model.Raca;

import java.util.UUID;

public record RacaDto(UUID id,
                      String nome){

    public RacaDto(Raca raca) {
        this(raca.getId(), raca.getNome());
    }
    // EspecieDto especie) {



}
