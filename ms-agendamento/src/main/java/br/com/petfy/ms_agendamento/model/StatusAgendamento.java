package br.com.petfy.ms_agendamento.model;

public enum StatusAgendamento {

    CONFIRMADO,               // O agendamento foi criado com sucesso.
    REALIZADO,                // A consulta aconteceu e foi concluída.
    CANCELADO_PELO_CLIENTE,   // O cliente cancelou.
    CANCELADO_PELA_CLINICA,   // A clínica cancelou.
    NAO_COMPARECEU             // O cliente não apareceu (no-show).

}
