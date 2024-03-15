package br.com.fiap.hackaton.ms.notificacao.listener;

import br.com.fiap.hackaton.ms.notificacao.domain.Evento;
import br.com.fiap.hackaton.ms.notificacao.domain.dto.NotificacaoDto;
import br.com.fiap.hackaton.ms.notificacao.service.NotificacaoEmailServiceImpl;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventoListener {

    private final NotificacaoEmailServiceImpl notificacaoEmailService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "notificacao", durable = "true"),
            exchange = @Exchange(value = "evento.topic", ignoreDeclarationExceptions = "true"),
            key = "notificacao.email")
    )
    public void listenerNotificacao(String payload){

        log.info("[RabbitMQ] - [INICIO] - Recebimento Evento.");

        try {

            var evento = new Gson().fromJson(payload, Evento.class);
            var metadados = evento.getMetadados();

            log.info("[RabbitMQ] - [SUCESSO] - Recebimento Evento - {}.", metadados.getUUID());
            log.info("[RabbitMQ] >>> Processar Mensagem - {}", evento.toString());
            notificacaoEmailService.notificar(obterDadosParaNotificacao());

        }catch (AmqpException exception){
            log.error("[RabbitMQ] - [ERRO] - Recebimento Evento - {}.", exception.getStackTrace());
        }finally {
            log.info("[RabbitMQ] - [FIM] - Recebimento Evento.");
        }

    }

    private NotificacaoDto obterDadosParaNotificacao() {

        return NotificacaoDto.builder()
                .destinatarios(Arrays.asList("williankaminski.santos@gmail.com"))
                .titulo("mock")
                .mensagem("mock")
                .build();
    }

}