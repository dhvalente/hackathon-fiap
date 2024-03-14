package br.com.fiap.hackaton.ms.notificacao.listener;

import br.com.fiap.hackaton.ms.notificacao.domain.Evento;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventoListener {

    @RabbitListener(queues = "notificacao.email")
    public void listenerNotificacao(String payload){

        log.info("[RabbitMQ] - [INICIO] - Recebimento Evento.");

        try {

            var evento = new Gson().fromJson(payload, Evento.class);
            var metadados = evento.getMetadados();

            log.info("[RabbitMQ] - [SUCESSO] - Recebimento Evento - {}.", metadados.getUUID());
            log.info("[RabbitMQ] >>> Processar Mensagem - {}", evento.toString());


        }catch (AmqpException exception){
            log.error("[RabbitMQ] - [ERRO] - Recebimento Evento - {}.", exception.getStackTrace());
        }finally {
            log.info("[RabbitMQ] - [FIM] - Recebimento Evento.");
        }

    }

}