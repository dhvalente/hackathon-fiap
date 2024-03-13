package br.com.fiap.hackaton.ms.notificacao.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventoListener {

    @RabbitListener(queues = {"evento.expiracao_reserva"})
    public void listener(String evento){
        log.info("Consumidor recebeu evento - {}", evento);
    }

}