package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.Exception.GenericException;
import br.com.fiap.hackathon.quartos.dtos.PredioDto;
import br.com.fiap.hackathon.quartos.entity.Localidade;
import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.mappers.PredioMapper;
import br.com.fiap.hackathon.quartos.mappers.QuartoMapper;
import br.com.fiap.hackathon.quartos.service.LocalidadeService;
import br.com.fiap.hackathon.quartos.service.PredioService;
import br.com.fiap.hackathon.quartos.service.QuartoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/predios")
public class PredioController {
  private final PredioService predioService;
  private final PredioMapper predioMapper;
  private final LocalidadeService localidadeService;
  private final QuartoService quartoService;
  private final QuartoMapper quartoMapper;

  public PredioController(
      PredioService predioService, PredioMapper predioMapper, LocalidadeService localidadeService,
      QuartoService quartoService,  QuartoMapper quartoMapper) {
    this.predioService = predioService;
    this.predioMapper = predioMapper;
    this.localidadeService = localidadeService;
    this.quartoService =  quartoService;
    this.quartoMapper = quartoMapper;
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
    if(!predioService.existsById(id)){
      throw new GenericException("Não exite um predio cadastrado com esse ID: " + id + "! Não é possível continuar!");
    }

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
      throw new GenericException("Localidade com ID " + predioDto.getLocalidadeId() + " não encontrado! Cadastre uma localidade com este ID antes de cadastrar um novo Predio!");
    }

    if(predioService.existsById(predioDto.getId())){
      throw new GenericException("Já exite um predio cadastrado com esse ID: " + predioDto.getId() + "! Não é possível continuar!");
    }

    if(!predioDto.getQuartos().stream().allMatch(quarto -> quarto.getPredioId().equals(predioDto.getId()))){
      throw new GenericException("Id do predio informado diferente do Id do predio informado em algum dos quartos!");
    }

    predioDto.getQuartos().stream().forEach(quarto -> {
      if(quartoService.existsById(quarto.getId())){
        throw new GenericException("Já existe um quarto cadastrado com esse ID: " + quarto.getId() + "! não é possível continuar!");
      }
    });
  //Busca a localidade, faz o insert do predio no banco, adiciona o predio na localida e da update atualizar a localidade.
    Localidade localidade = localidadeService.getLocalidadeById(predioDto.getLocalidadeId());
    Predio predio = predioMapper.toEntity(predioDto);
    predioService.createPredio(predio);
    localidade.getPredios().add(predio);
    localidadeService.updateLocalidade(predioDto.getLocalidadeId(), localidade);

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
    if(!predioService.existsById(id)){
      throw new GenericException("Não exite um predio cadastrado com esse ID: " + id + "! Não é possível continuar!");
    }

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
    if(!predioService.existsById(id)){
      throw new GenericException("Não exite um predio cadastrado com esse ID: " + id + "! Não é possível continuar!");
    }
    predioService.deletePredio(id);
    return ResponseEntity.ok().build();
  }
}
