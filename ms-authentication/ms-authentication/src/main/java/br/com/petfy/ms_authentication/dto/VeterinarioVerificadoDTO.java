package br.com.petfy.ms_authentication.dto;

public record VeterinarioVerificadoDTO( String nome,
                                        String crmv,
                                        String situacao,
                                        String uf,
                                        String area,
                                        String dataInscricao,
                                        String login,
                                        String password) {
}
