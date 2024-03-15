package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.Exception.GenericException;
import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import br.com.fiap.hackathon.quartos.entity.Localidade;
import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.mappers.QuartoMapper;
import br.com.fiap.hackathon.quartos.service.LocalidadeService;
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

    private final LocalidadeService localidadeService;

    public QuartoController(QuartoService quartoService, QuartoMapper quartoMapper, PredioService predioService, LocalidadeService localidadeService) {
        this.quartoService = quartoService;
        this.quartoMapper = quartoMapper;
        this.predioService = predioService;
        this.localidadeService = localidadeService;
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
        if (!quartoService.existsById(id)) {
            throw new GenericException("Não existe um quarto cadastrado com esse ID: " + id + "! não é possível continuar!");
        }
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
            throw new GenericException("Predio com ID " + quartoDto.getPredioId() + " não encontrado! Cadastre o predio com este ID antes de cadastrar um novo quarto!");
        }

        if (quartoService.existsById(quartoDto.getId())) {
            throw new GenericException("Já existe um quarto cadastrado com esse ID: " + quartoDto.getId() + "! não é possível continuar!");
        }

        Predio predio = predioService.getPredioById(quartoDto.getPredioId());
        Localidade localidade = localidadeService.getLocalidadeById(predio.getLocalidadeId());

        Predio predioLocalidade = localidade.getPredios().stream().filter(p -> p.equals(predio)).findFirst().get();

        Quarto quarto = quartoMapper.toEntity(quartoDto);
        quarto = quartoService.createQuarto(quarto);
        predioLocalidade.getQuartos().add(quarto);
        predioService.updatePredio(quartoDto.getPredioId(), predioLocalidade);
        localidadeService.updateLocalidade(localidade.getId(), localidade);


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
        if (!quartoService.existsById(id)) {
            throw new GenericException("Não existe um quarto cadastrado com esse ID: " + id + "! não é possível continuar!");
        }

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
        if (!quartoService.existsById(id)) {
            throw new GenericException("Não existe um quarto cadastrado com esse ID: " +id + "! não é possível continuar!");
        }
        quartoService.deleteQuarto(id);
        return ResponseEntity.ok().build();
    }
}
