package br.com.fiap.hackaton.ms.reservas.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "spring.rabbitmq-channel")
public class ApplicationProperties {

    private String exchangeName;
    private String queueName;
    private String routingKey;

}