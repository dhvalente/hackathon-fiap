package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.PredioDto;
import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.mappers.PredioMapper;
import br.com.fiap.hackathon.quartos.service.LocalidadeService;
import br.com.fiap.hackathon.quartos.service.PredioService;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/predios")
public class PredioController {
  private final PredioService predioService;
  private final PredioMapper predioMapper;

  private final LocalidadeService localidadeService;

  public PredioController(
      PredioService predioService, PredioMapper predioMapper, LocalidadeService localidadeService) {
    this.predioService = predioService;
    this.predioMapper = predioMapper;
    this.localidadeService = localidadeService;
  }

  @GetMapping
  @Operation(
      summary = "Lista todos os prédios",
      responses = {
        @ApiResponse(
            description = "Sucesso",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = PredioDto.class)))
      })
  public ResponseEntity<List<PredioDto>> getAllPredios() {
    return ResponseEntity.ok(
        predioService.getAllPredios().stream()
            .map(predioMapper::toDto)
            .collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Busca um prédio pelo ID",
      responses = {
        @ApiResponse(
            description = "Sucesso",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = PredioDto.class))),
        @ApiResponse(description = "Não encontrado", responseCode = "404")
      })
  public ResponseEntity<PredioDto> getPredioById(@PathVariable String id) {
    return ResponseEntity.ok(predioMapper.toDto(predioService.getPredioById(id)));
  }

  @PostMapping
  @Operation(
      summary = "Cria um novo prédio",
      responses = {
        @ApiResponse(
            description = "Criado",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = PredioDto.class)))
      })
  public ResponseEntity<PredioDto> createPredio(@RequestBody @Valid PredioDto predioDto) {
    if (!localidadeService.existsById(predioDto.getLocalidadeId())) {

      return ResponseEntity.badRequest().body(null);
    }

    Predio predio = predioMapper.toEntity(predioDto);
    predio = predioService.createPredio(predio);

    return ResponseEntity.ok(predioMapper.toDto(predio));
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Atualiza um prédio existente",
      responses = {
        @ApiResponse(
            description = "Sucesso",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = PredioDto.class))),
        @ApiResponse(description = "Não encontrado", responseCode = "404")
      })
  public ResponseEntity<PredioDto> updatePredio(
      @PathVariable String id, @RequestBody PredioDto predioDto) {
    Predio predio = predioMapper.toEntity(predioDto);
    predio = predioService.updatePredio(id, predio);
    return ResponseEntity.ok(predioMapper.toDto(predio));
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Deleta um prédio",
      responses = {
        @ApiResponse(description = "Sucesso", responseCode = "200"),
        @ApiResponse(description = "Não encontrado", responseCode = "404")
      })
  public ResponseEntity<Void> deletePredio(@PathVariable String id) {
    predioService.deletePredio(id);
    return ResponseEntity.ok().build();
  }
}
