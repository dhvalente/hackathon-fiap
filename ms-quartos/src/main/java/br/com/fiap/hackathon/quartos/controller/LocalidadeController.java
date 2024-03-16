package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.Exception.GenericException;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Lista todas as localidades",
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LocalidadeDto.class)))
            })
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
    @Operation(
            summary = "Busca uma localidade pelo ID",
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LocalidadeDto.class))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404")
            })
    public ResponseEntity<LocalidadeDto> getLocalidadeById(@PathVariable String id) {
        if (!localidadeService.existsById(id)) {
            throw new GenericException("Não existe uma localidade cadastrada com esse ID: " + id + "! não é possível continuar!");
        }
        return ResponseEntity.ok(localidadeMapper.toDto(localidadeService.getLocalidadeById(id)));
    }

    @PostMapping
    @Operation(
            summary = "Cria uma nova localidade",
            responses = {
                    @ApiResponse(
                            description = "Criado",
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = LocalidadeDto.class)))
            })
    public ResponseEntity<LocalidadeDto> createLocalidade(
            @RequestBody @Valid LocalidadeDto localidadeDto) {

        if (localidadeService.existsById(localidadeDto.getId())) {
            throw new GenericException("Já existe uma localidade cadastrada com esse ID: " + localidadeDto.getId() + "! não é possível continuar!");
        }

        Localidade localidade = localidadeMapper.toEntity(localidadeDto);

        for (PredioDto predioDto : localidadeDto.getPredios()) {
            Predio predio = predioMapper.toEntity(predioDto);
            if (predioService.existsById(predioDto.getId())) {
                throw new GenericException("O prédio com ID " + predioDto.getId() + " já está cadastrado!");
            } else {
                predioService.createPredio(predio);
            }

            for (QuartoDto quartoDto : predioDto.getQuartos()) {
                Quarto quarto = quartoMapper.toEntity(quartoDto);
                quarto = quartoService.createQuarto(quarto);

            }
        }
        localidade = localidadeService.createLocalidade(localidade);
        return ResponseEntity.ok(localidadeMapper.toDto(localidade));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza uma localidade existente",
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LocalidadeDto.class))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404")
            })
    public ResponseEntity<LocalidadeDto> updateLocalidade(
            @PathVariable String id, @RequestBody LocalidadeDto localidadeDto) {

        if (!localidadeService.existsById(id)) {
            throw new GenericException("Não existe uma localidade cadastrada com esse ID: " + id + "! não é possível continuar!");
        }

        Localidade localidade = localidadeMapper.toEntity(localidadeDto);
        localidade = localidadeService.updateLocalidade(id, localidade);

        for (PredioDto predioDto : localidadeDto.getPredios()) {
            Predio predio = predioMapper.toEntity(predioDto);
            if(!predio.getLocalidadeId().equals(localidade.getId())){
                throw new GenericException("ID da localidade diferente do ID da localidade informado no Predio!");
            }
            if (!predioService.existsById(predioDto.getId())) {
                throw new GenericException("Não existe o prédio com ID " + predioDto.getId());
            } else {
                predio = predioService.updatePredio(predioDto.getId(), predio);
            }

            for (QuartoDto quartoDto : predioDto.getQuartos()) {
                Quarto quarto = quartoMapper.toEntity(quartoDto);
                if (!quartoService.existsById(quartoDto.getId())) {
                    throw new GenericException("Não existe o quarto com ID " + quartoDto.getId());
                } else {
                    quartoService.updateQuarto(quartoDto.getId(), quarto);
                }

            }
        }

        return ResponseEntity.ok(localidadeMapper.toDto(localidade));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta uma localidade",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200"),
                    @ApiResponse(description = "Não encontrado", responseCode = "404")
            })
    public ResponseEntity<Void> deleteLocalidade(@PathVariable String id) {
        if (!localidadeService.existsById(id)) {
            throw new GenericException("Não existe uma localidade cadastrada com esse ID: " + id + "! não é possível continuar!");
        }
        localidadeService.deleteLocalidade(id);
        return ResponseEntity.ok().build();
    }
}
