package br.com.fiap.hackaton.ms.reservas.controller;

import br.com.fiap.hackaton.ms.reservas.domain.Reserva;
import br.com.fiap.hackaton.ms.reservas.dtos.ReservaDto;
import br.com.fiap.hackaton.ms.reservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@Slf4j
public class ReservaController {

  @Autowired private ReservaService reservaService;

  @PostMapping
  @Operation(summary = "Cria uma nova reserva")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reserva criada com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Reserva.class))
            }),
        @ApiResponse(responseCode = "400", description = "Erro na requisição")
      })
  public ResponseEntity<Reserva> createReserva(@RequestBody @Valid ReservaDto reservaDTO) {
      Reserva reserva = reservaService.createReserva(reservaDTO);
      return ResponseEntity.ok(reserva);
  }

  @GetMapping
  @Operation(summary = "Lista todas as reservas")
  public ResponseEntity<List<Reserva>> getAllReservas() {
    List<Reserva> reservas = reservaService.findAllReservas();
    return ResponseEntity.ok(reservas);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Busca uma reserva pelo ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reserva encontrada",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Reserva.class))
            }),
        @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
      })
  public ResponseEntity<Reserva> getReservaById(@PathVariable String id) {
      Reserva reserva = reservaService.findReservaById(id);
      return ResponseEntity.ok(reserva);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualiza uma reserva existente")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reserva atualizada com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Reserva.class))
            }),
        @ApiResponse(responseCode = "400", description = "Erro ao atualizar a reserva")
      })
  public ResponseEntity<Reserva> updateReserva(
      @PathVariable String id, @RequestBody ReservaDto reservaDTO) {
      Reserva updatedReserva = reservaService.updateReserva(id, reservaDTO);
      return ResponseEntity.ok(updatedReserva);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Remove uma reserva pelo ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Reserva removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
      })
  public ResponseEntity<Void> deleteReserva(@PathVariable String id) {
      reservaService.deleteReserva(id);
      return ResponseEntity.ok().build();
  }
}
