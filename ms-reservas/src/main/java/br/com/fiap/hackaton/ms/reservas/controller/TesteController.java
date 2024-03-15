package br.com.fiap.hackaton.ms.reservas.controller;

import br.com.fiap.hackaton.ms.reservas.domain.Evento;
import br.com.fiap.hackaton.ms.reservas.domain.EventoExpiracaoReserva;
import br.com.fiap.hackaton.ms.reservas.domain.Metadados;
import br.com.fiap.hackaton.ms.reservas.domain.TipoEventoEnum;
import br.com.fiap.hackaton.ms.reservas.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("v1/evento")
@RequiredArgsConstructor
public class TesteController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<Void> gerarEventoMock(){

        Metadados metadados = new Metadados();
        metadados.setUUID(UUID.randomUUID().toString());
        metadados.setDataHora(LocalDateTime.now().toString());
        metadados.setTipoEvento(TipoEventoEnum.EXPIRACAO_RESERVA);

        EventoExpiracaoReserva eventoExpiracaoReserva = new EventoExpiracaoReserva();
        eventoExpiracaoReserva.setMensagem("Reserva Expirada");

        Evento evento = new Evento();
        evento.setMetadados(metadados);
        evento.setPayload(eventoExpiracaoReserva);

        eventoService.publicarEvento(evento);

        return new ResponseEntity<Void>( HttpStatus.OK );
    }
}
