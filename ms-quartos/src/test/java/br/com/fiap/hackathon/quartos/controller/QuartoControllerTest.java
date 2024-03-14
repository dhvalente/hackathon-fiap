package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.mappers.QuartoMapper;
import br.com.fiap.hackathon.quartos.service.PredioService;
import br.com.fiap.hackathon.quartos.service.QuartoService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


import static org.mockito.Mockito.*;

class QuartoControllerTest {
    @Mock
    QuartoService quartoService;
    @Mock
    QuartoMapper quartoMapper;
    @Mock
    PredioService predioService;
    @InjectMocks
    QuartoController quartoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getting all available quartos")
    void testGetAllQuartos() {
        when(quartoService.getAllQuartos()).thenReturn(List.of(new Quarto()));
        when(quartoMapper.toDto(any())).thenReturn(new QuartoDto());

        ResponseEntity<List<QuartoDto>> result = quartoController.getAllQuartos();
        Assertions.assertNotEquals(ResponseEntity.badRequest().build(), result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Test getting quarto by id")
    void testGetQuartoById() {
        QuartoDto expectedDto = new QuartoDto();
        when(quartoService.getQuartoById(anyString())).thenReturn(new Quarto());
        when(quartoMapper.toDto(any())).thenReturn(expectedDto);

        ResponseEntity<QuartoDto> result = quartoController.getQuartoById("id");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(expectedDto, result.getBody());
    }

    // TODO: Review this test
    @Test
    @DisplayName("Test creating a new quarto")
    void testCreateQuarto() {
        QuartoDto inputDto = new QuartoDto();
        inputDto.setPredioId("validPredioId");
        Quarto quarto = new Quarto();
        quarto.setPredioId(inputDto.getPredioId());
        QuartoDto expectedDto = new QuartoDto();
        when(predioService.existsById(inputDto.getPredioId())).thenReturn(true);
        when(quartoService.createQuarto(any(Quarto.class))).thenReturn(quarto);
        when(quartoMapper.toDto(any(Quarto.class))).thenReturn(expectedDto);

        ResponseEntity<QuartoDto> result = quartoController.createQuarto(inputDto);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(expectedDto, result.getBody());
    }

    // TODO: Review this test
    @Test
    @DisplayName("Test updating a quarto")
    void testUpdateQuarto() {
        QuartoDto expectedDto = new QuartoDto();
        when(quartoService.updateQuarto(anyString(), any(Quarto.class))).thenReturn(new Quarto());
        when(quartoMapper.toDto(any(Quarto.class))).thenReturn(expectedDto);

        ResponseEntity<QuartoDto> result = quartoController.updateQuarto("id", new QuartoDto());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(expectedDto, result.getBody());
    }

    @Test
    @DisplayName("Test deleting a quarto by id")
    void testDeleteQuarto() {
        ResponseEntity<Void> result = quartoController.deleteQuarto("id");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Test creating a new quarto with invalid predio ID")
    void testCreateQuartoWithInvalidPredioId() {
        QuartoDto inputDto = new QuartoDto();
        inputDto.setPredioId("invalidPredioId");
        when(predioService.existsById(inputDto.getPredioId())).thenReturn(false);

        ResponseEntity<QuartoDto> result = quartoController.createQuarto(inputDto);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Assertions.assertNull(result.getBody());
    }

    @Test
    @DisplayName("Test getting a non-existent quarto by id")
    void testGetNonExistentQuartoById() {
        when(quartoService.getQuartoById(anyString())).thenReturn(null);

        ResponseEntity<QuartoDto> result = quartoController.getQuartoById("nonExistentId");
        Assertions.assertNotEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        Assertions.assertNull(result.getBody());
    }

    @Test
    @DisplayName("Test updating a non-existent quarto")
    void testUpdateNonExistentQuarto() {
        when(quartoService.updateQuarto(anyString(), any(Quarto.class))).thenReturn(null);

        ResponseEntity<QuartoDto> result = quartoController.updateQuarto("nonExistentId", new QuartoDto());
        Assertions.assertNotEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        Assertions.assertNull(result.getBody());
    }

}