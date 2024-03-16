package br.com.fiap.hackaton.ms.reservas.service;

import br.com.fiap.hackaton.ms.reservas.configuration.ApplicationProperties;
import br.com.fiap.hackaton.ms.reservas.domain.Evento;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventoService {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    @SneakyThrows
    public void publicarEvento(Evento evento) {

        log.info("[RabbitMQ] - [INICIO] - Publicacao Evento.");

        try {

            var metadados = evento.getMetadados();
            var payload = new Gson().toJson(evento);
            rabbitTemplate.convertAndSend(properties.getExchangeName(), properties.getRoutingKey(), payload);

            log.info("[RabbitMQ] - [SUCESSO] - Publicacao Evento - {}.", metadados.getUUID());

        }catch (AmqpException exception){
            log.info("[RabbitMQ] - [ERRO] - Processamento Evento - {}.", exception.getStackTrace());
        }finally {
            log.info("[RabbitMQ] - [FIM] - Publicacao Evento.");
        }

    }

}
