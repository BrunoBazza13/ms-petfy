package br.com.petfy.ms_clinica.dto;

import br.com.petfy.ms_clinica.model.Clinica;
import org.springframework.lang.Contract;


public record ClinicaVerificadaDTO(
        String nome,
        String cnpj,
        String situacaoCadastral,
        EnderecoResponseDto endereco

) {
    //  NOVO CONSTRUTOR AUXILIAR
    // Ele recebe a entidade Clinica e os dados extras que vêm da API
    public ClinicaVerificadaDTO(Clinica clinica, String situacaoCadastrals) {
        this(
                clinica.getNome(),
                clinica.getCnpj(),
                situacaoCadastrals, // Usa a situação vinda da API
                new EnderecoResponseDto(clinica.getEndereco()) // Usa o outro DTO para o endereço
        );
    }


}