package br.com.fiap.hackaton.ms.notificacao.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "spring.notificacao")
public class NotificacaoProperties {

    private String from;
    private String pass;
}
