package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.LocalidadeDto;
import br.com.fiap.hackathon.quartos.dtos.PredioDto;
import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import br.com.fiap.hackathon.quartos.entity.Localidade;
import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.mappers.LocalidadeMapper;
import br.com.fiap.hackathon.quartos.mappers.PredioMapper;
import br.com.fiap.hackathon.quartos.mappers.QuartoMapper;
import br.com.fiap.hackathon.quartos.service.LocalidadeService;
import br.com.fiap.hackathon.quartos.service.PredioService;
import br.com.fiap.hackathon.quartos.service.QuartoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/localidades")
public class LocalidadeController {
  private final LocalidadeService localidadeService;
  private final LocalidadeMapper localidadeMapper;

  private final PredioService predioService;
  private final PredioMapper predioMapper;
  private final QuartoService quartoService;
  private final QuartoMapper quartoMapper;

  public LocalidadeController(
      LocalidadeService localidadeService,
      LocalidadeMapper localidadeMapper,
      PredioService predioService,
      PredioMapper predioMapper,
      QuartoService quartoService,
      QuartoMapper quartoMapper) {
    this.localidadeService = localidadeService;
    this.localidadeMapper = localidadeMapper;
    this.predioService = predioService;
    this.predioMapper = predioMapper;
    this.quartoService = quartoService;
    this.quartoMapper = quartoMapper;
  }

  @GetMapping
  public ResponseEntity<List<LocalidadeDto>> getAllLocalidades() {
    return ResponseEntity.ok(
        localidadeService.getAllLocalidades().stream()
            .map(
                localidade -> {
                  LocalidadeDto localidadeDto = localidadeMapper.toDto(localidade);
                  for (PredioDto predioDto : localidadeDto.getPredios()) {
                    List<QuartoDto> quartosDto =
                        predioDto.getQuartos().stream()
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    predioDto.setQuartos(quartosDto);
                  }
                  return localidadeDto;
                })
            .collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LocalidadeDto> getLocalidadeById(@PathVariable String id) {
    return ResponseEntity.ok(localidadeMapper.toDto(localidadeService.getLocalidadeById(id)));
  }

  @PostMapping
  public ResponseEntity<LocalidadeDto> createLocalidade(
      @RequestBody @Valid LocalidadeDto localidadeDto) {
    Localidade localidade = localidadeMapper.toEntity(localidadeDto);
    localidade = localidadeService.createLocalidade(localidade);

    for (PredioDto predioDto : localidadeDto.getPredios()) {
      Predio predio = predioMapper.toEntity(predioDto);
      predio = predioService.createPredio(predio);

      for (QuartoDto quartoDto : predioDto.getQuartos()) {
        Quarto quarto = quartoMapper.toEntity(quartoDto);
        quartoService.createQuarto(quarto);
      }
    }

    return ResponseEntity.ok(localidadeMapper.toDto(localidade));
  }

  @PutMapping("/{id}")
  public ResponseEntity<LocalidadeDto> updateLocalidade(
      @PathVariable String id, @RequestBody LocalidadeDto localidadeDto) {
    Localidade localidade = localidadeMapper.toEntity(localidadeDto);
    localidade = localidadeService.updateLocalidade(id, localidade);

    for (PredioDto predioDto : localidadeDto.getPredios()) {
      Predio predio = predioMapper.toEntity(predioDto);
      predio = predioService.updatePredio(predioDto.getId(), predio);

      for (QuartoDto quartoDto : predioDto.getQuartos()) {
        Quarto quarto = quartoMapper.toEntity(quartoDto);
        quartoService.updateQuarto(quartoDto.getId(), quarto);
      }
    }

    return ResponseEntity.ok(localidadeMapper.toDto(localidade));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLocalidade(@PathVariable String id) {
    localidadeService.deleteLocalidade(id);
    return ResponseEntity.ok().build();
  }
}
