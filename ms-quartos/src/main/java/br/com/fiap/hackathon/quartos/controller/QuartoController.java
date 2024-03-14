package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.mappers.QuartoMapper;
import br.com.fiap.hackathon.quartos.service.QuartoService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/quartos")
public class QuartoController {
  private final QuartoService quartoService;
  private final QuartoMapper quartoMapper;

  public QuartoController(QuartoService quartoService, QuartoMapper quartoMapper) {
    this.quartoService = quartoService;
    this.quartoMapper = quartoMapper;
  }

  @GetMapping
  public ResponseEntity<List<QuartoDto>> getAllQuartos() {
    return ResponseEntity.ok(
        quartoService.getAllQuartos().stream()
            .map(quartoMapper::toDto)
            .collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuartoDto> getQuartoById(@PathVariable String id) {
    return ResponseEntity.ok(quartoMapper.toDto(quartoService.getQuartoById(id)));
  }

  @PostMapping
  public ResponseEntity<QuartoDto> createQuarto(@RequestBody QuartoDto quartoDto) {
    Quarto quarto = quartoMapper.toEntity(quartoDto);
    quarto = quartoService.createQuarto(quarto);
    return ResponseEntity.ok(quartoMapper.toDto(quarto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<QuartoDto> updateQuarto(
      @PathVariable String id, @RequestBody QuartoDto quartoDto) {
    Quarto quarto = quartoMapper.toEntity(quartoDto);
    quarto = quartoService.updateQuarto(id, quarto);
    return ResponseEntity.ok(quartoMapper.toDto(quarto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteQuarto(@PathVariable String id) {
    quartoService.deleteQuarto(id);
    return ResponseEntity.ok().build();
  }
}
