package br.com.petfy.ms_clinica.dto;

import br.com.petfy.ms_clinica.model.Endereco;

public record EnderecoResponseDto(
        String logradouro,
        int numero,
        String bairro,
        String cidade,
        String estado,
        String cep
) {
    public EnderecoResponseDto(Endereco endereco) {
        this(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getCep()
        );
    }
}