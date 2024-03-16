package br.com.fiap.hackaton.ms.reservas.controller;

import br.com.fiap.hackaton.ms.reservas.domain.Reserva;
import br.com.fiap.hackaton.ms.reservas.dtos.ReservaDto;
import br.com.fiap.hackaton.ms.reservas.service.ReservaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/reservas")
public class ReservaController {

  @Autowired private ReservaService reservaService;

  @PostMapping
  public ResponseEntity<Reserva> createReserva(@RequestBody ReservaDto reservaDTO) {
    try {
      Reserva reserva = reservaService.createReserva(reservaDTO);
      return ResponseEntity.ok(reserva);
    } catch (IllegalStateException e) {
      log.error("Error creating reservation: {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }
}
