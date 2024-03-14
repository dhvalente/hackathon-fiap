package br.com.fiap.hackaton.ms.reservas.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final ApplicationProperties properties;

    @Bean
    public Queue getQueue(){
        return new Queue(properties.getQueueName(), false, false, false);
    }

    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange(properties.getExchangeName(), false, false);
    }

    @Bean
    public Binding getBinding(){
        return BindingBuilder
                .bind(getQueue())
                .to(getDirectExchange())
                .with(properties.getRoutingKey());
    }

}