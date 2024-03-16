package br.com.fiap.hackaton.ms.notificacao.service;

import br.com.fiap.hackaton.ms.notificacao.domain.EventoExpiracaoReserva;

public interface NotificacaoService {

    Boolean notificar(EventoExpiracaoReserva eventoExpiracaoReserva);
}
