package br.com.fiap.hackathon.quartos.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "API de Gestão de Quartos",
            version = "v1",
            description =
                "Esta API é responsável pela gestão de quartos, prédios e localidades de uma rede de hotéis.",
            contact =
                @Contact(
                    name = "Grupo 29",
                    email = "contato@fiap.com.br",
                    url = "https://www.fiap.com.br")),
    servers = @Server(url = "/", description = "Servidor Principal"))
public class SwaggerConfig {}
