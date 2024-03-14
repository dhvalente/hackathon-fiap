package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.PredioDto;
import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.mappers.PredioMapper;
import br.com.fiap.hackathon.quartos.service.LocalidadeService;
import br.com.fiap.hackathon.quartos.service.PredioService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

class PredioControllerTest {
    @Mock
    PredioService predioService;
    @Mock
    PredioMapper predioMapper;
    @Mock
    LocalidadeService localidadeService;
    @InjectMocks
    PredioController predioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getting all predios")
    void testGetAllPredios() {
        when(predioService.getAllPredios()).thenReturn(List.of(new Predio()));
        when(predioMapper.toDto(any())).thenReturn(new PredioDto());

        ResponseEntity<List<PredioDto>> result = predioController.getAllPredios();
        Assertions.assertNotEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(new PredioDto())), result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Test getting predio by id")
    void testGetPredioById() {
        PredioDto expectedDto = new PredioDto();
        when(predioService.getPredioById(anyString())).thenReturn(new Predio());
        when(predioMapper.toDto(any())).thenReturn(expectedDto);

        ResponseEntity<PredioDto> result = predioController.getPredioById("id");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(expectedDto, result.getBody());
    }

    @Test
    @DisplayName("Test creating a predio")
    void testCreatePredio() {
        PredioDto expectedDto = new PredioDto();
        when(predioService.createPredio(any())).thenReturn(new Predio());
        when(predioMapper.toDto(any())).thenReturn(expectedDto);
        when(predioMapper.toEntity(any())).thenReturn(new Predio());
        when(localidadeService.existsById(anyString())).thenReturn(true);

        ResponseEntity<PredioDto> result = predioController.createPredio(new PredioDto());
        Assertions.assertNotEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotEquals(expectedDto, result.getBody());
    }

    @Test
    @DisplayName("Test updating a predio")
    void testUpdatePredio() {
        PredioDto expectedDto = new PredioDto();
        when(predioService.updatePredio(anyString(), any())).thenReturn(new Predio());
        when(predioMapper.toDto(any())).thenReturn(expectedDto);
        when(predioMapper.toEntity(any())).thenReturn(new Predio());

        ResponseEntity<PredioDto> result = predioController.updatePredio("id", new PredioDto());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(expectedDto, result.getBody());
    }

    @Test
    @DisplayName("Test deleting a predio")
    void testDeletePredio() {
        ResponseEntity<Void> result = predioController.deletePredio("id");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}