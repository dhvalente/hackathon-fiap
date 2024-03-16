package br.com.fiap.hackaton.ms.notificacao.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificacaoDto {

    private String titulo;
    private String destinatario;
    private String mensagem;

}