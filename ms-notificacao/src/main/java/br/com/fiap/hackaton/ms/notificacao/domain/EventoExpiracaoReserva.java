package br.com.fiap.hackaton.ms.notificacao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoExpiracaoReserva implements Serializable {

    private String destinatario;
    private String assunto;
    private String mensagem;
}