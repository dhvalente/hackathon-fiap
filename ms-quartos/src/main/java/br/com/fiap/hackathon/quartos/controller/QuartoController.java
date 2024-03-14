package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.mappers.QuartoMapper;
import br.com.fiap.hackathon.quartos.service.PredioService;
import br.com.fiap.hackathon.quartos.service.QuartoService;
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
@RequestMapping("api/v1/quartos")
public class QuartoController {
  private final QuartoService quartoService;
  private final QuartoMapper quartoMapper;

  private final PredioService predioService;

  public QuartoController(
      QuartoService quartoService, QuartoMapper quartoMapper, PredioService predioService) {
    this.quartoService = quartoService;
    this.quartoMapper = quartoMapper;
    this.predioService = predioService;
  }

  @GetMapping
  @Operation(
      summary = "Lista todos os quartos",
      responses = {
        @ApiResponse(
            description = "Sucesso",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = QuartoDto.class)))
      })
  public ResponseEntity<List<QuartoDto>> getAllQuartos() {
    return ResponseEntity.ok(
        quartoService.getAllQuartos().stream()
            .map(quartoMapper::toDto)
            .collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Busca um quarto pelo ID",
      responses = {
        @ApiResponse(
            description = "Sucesso",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = QuartoDto.class))),
        @ApiResponse(description = "Não encontrado", responseCode = "404")
      })
  public ResponseEntity<QuartoDto> getQuartoById(@PathVariable String id) {
    return ResponseEntity.ok(quartoMapper.toDto(quartoService.getQuartoById(id)));
  }

  @PostMapping
  @Operation(
      summary = "Cria um novo quarto",
      responses = {
        @ApiResponse(
            description = "Criado",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = QuartoDto.class))),
        @ApiResponse(description = "Erro de validação", responseCode = "400")
      })
  public ResponseEntity<QuartoDto> createQuarto(@RequestBody @Valid QuartoDto quartoDto) {
    // Verificar se o prédio associado existe
    if (!predioService.existsById(quartoDto.getPredioId())) {
      return ResponseEntity.badRequest().build();
    }

    Quarto quarto = quartoMapper.toEntity(quartoDto);
    quarto.setPredioId(quartoDto.getPredioId());
    quarto = quartoService.createQuarto(quarto);

    return ResponseEntity.ok(quartoMapper.toDto(quarto));
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Atualiza um quarto existente",
      responses = {
        @ApiResponse(
            description = "Sucesso",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = QuartoDto.class))),
        @ApiResponse(description = "Não encontrado", responseCode = "404"),
        @ApiResponse(description = "Erro de validação", responseCode = "400")
      })
  public ResponseEntity<QuartoDto> updateQuarto(
      @PathVariable String id, @RequestBody QuartoDto quartoDto) {
    Quarto quarto = quartoMapper.toEntity(quartoDto);
    quarto = quartoService.updateQuarto(id, quarto);
    return ResponseEntity.ok(quartoMapper.toDto(quarto));
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Deleta um quarto",
      responses = {
        @ApiResponse(description = "Sucesso", responseCode = "200"),
        @ApiResponse(description = "Não encontrado", responseCode = "404")
      })
  public ResponseEntity<Void> deleteQuarto(@PathVariable String id) {
    quartoService.deleteQuarto(id);
    return ResponseEntity.ok().build();
  }
}
