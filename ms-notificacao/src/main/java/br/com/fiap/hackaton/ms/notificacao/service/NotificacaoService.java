package br.com.fiap.hackaton.ms.notificacao.service;

import br.com.fiap.hackaton.ms.notificacao.domain.dto.NotificacaoDto;

public interface NotificacaoService {

    Boolean notificar(NotificacaoDto notificacao);
}
