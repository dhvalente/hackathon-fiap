package br.com.fiap.hackaton.dto;

import java.io.Serializable;

public class EventoExpiracaoReserva implements Serializable {

    private String mensagem;

    public EventoExpiracaoReserva() {
    }

    public EventoExpiracaoReserva(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "EventoExpiracaoReserva{" +
                "mensagem='" + mensagem + '\'' +
                '}';
    }
}