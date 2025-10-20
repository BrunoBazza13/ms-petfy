package br.com.petfy.ms_pet.dto;

import br.com.petfy.ms_pet.model.Especie;
import br.com.petfy.ms_pet.model.Pet;
import br.com.petfy.ms_pet.model.Raca;

import java.util.UUID;

public record PetResquestDto( String nome,        // Nome do pet
                              String porte,        // Porte do pet (ex: "Pequeno", "Médio", "Grande")
                              int idade,
                              char sexo,
                              double peso,
                              UUID especieId,     // ID da espécie vindo do seu banco de dados
                              UUID racaId) {      // ID da raça vindo do seu banco de dado){         // Sexo do pet (ex: "Macho", "Fêmea")
}
