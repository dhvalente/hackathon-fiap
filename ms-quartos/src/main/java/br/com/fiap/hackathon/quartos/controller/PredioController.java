package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.PredioDto;
import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.mappers.PredioMapper;
import br.com.fiap.hackathon.quartos.service.PredioService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/predios")
public class PredioController {
  private final PredioService predioService;
  private final PredioMapper predioMapper;

  public PredioController(PredioService predioService, PredioMapper predioMapper) {
    this.predioService = predioService;
    this.predioMapper = predioMapper;
  }

  @GetMapping
  public ResponseEntity<List<PredioDto>> getAllPredios() {
    return ResponseEntity.ok(
        predioService.getAllPredios().stream()
            .map(predioMapper::toDto)
            .collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PredioDto> getPredioById(@PathVariable String id) {
    return ResponseEntity.ok(predioMapper.toDto(predioService.getPredioById(id)));
  }

  @PostMapping
  public ResponseEntity<PredioDto> createPredio(@RequestBody PredioDto predioDto) {
    Predio predio = predioMapper.toEntity(predioDto);
    predio = predioService.createPredio(predio);
    return ResponseEntity.ok(predioMapper.toDto(predio));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PredioDto> updatePredio(
      @PathVariable String id, @RequestBody PredioDto predioDto) {
    Predio predio = predioMapper.toEntity(predioDto);
    predio = predioService.updatePredio(id, predio);
    return ResponseEntity.ok(predioMapper.toDto(predio));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePredio(@PathVariable String id) {
    predioService.deletePredio(id);
    return ResponseEntity.ok().build();
  }
}
