package br.com.fiap.hackaton.ms.reservas.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "API de Gestão de Reservas",
            version = "v1",
            description =
                "Esta API é responsável pela gestão e realização de reservas na aplicação.",
            contact =
                @Contact(
                    name = "Grupo 29",
                    email = "contato@fiap.com.br",
                    url = "https://www.fiap.com.br")),
    servers = @Server(url = "/", description = "Servidor Principal"))
public class SwaggerConfig {}
