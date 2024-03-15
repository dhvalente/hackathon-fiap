package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.PredioDto;
import br.com.fiap.hackathon.quartos.entity.Localidade;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testGetAllPredios() {
        List<Predio> predios = new ArrayList<>();
        Predio predio = new Predio();
        predios.add(predio);

        List<PredioDto> prediosDto = new ArrayList<>();
        PredioDto predioDto = new PredioDto();
        prediosDto.add(predioDto);

        when(predioService.getAllPredios()).thenReturn(predios);
        when(predioMapper.toDto(predio)).thenReturn(predioDto);

        ResponseEntity<List<PredioDto>> response = predioController.getAllPredios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(prediosDto, response.getBody());
    }

    @Test
    void testGetPredioById() {
        String id = "1";
        Predio predio = new Predio();
        predio.setId(id);

        PredioDto predioDto = new PredioDto();
        predioDto.setId(id);

        when(predioService.existsById(id)).thenReturn(true);
        when(predioService.getPredioById(id)).thenReturn(predio);
        when(predioMapper.toDto(predio)).thenReturn(predioDto);

        ResponseEntity<PredioDto> response = predioController.getPredioById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(predioDto, response.getBody());
    }

    @Test
    void testCreatePredio() {
        // Mock de entrada
        PredioDto predioDto = new PredioDto();

        Localidade localidade = new Localidade();

        Predio predio = new Predio();

        when(localidadeService.existsById(any())).thenReturn(true);
        when(predioService.existsById(any())).thenReturn(false);
        when(localidadeService.getLocalidadeById(any())).thenReturn(localidade);
        when(predioMapper.toEntity(predioDto)).thenReturn(predio);
        when(predioService.createPredio(predio)).thenReturn(predio);
        when(predioMapper.toDto(predio)).thenReturn(predioDto);

        ResponseEntity<PredioDto> response = predioController.createPredio(predioDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(predioDto, response.getBody());
    }

    @Test
    void testUpdatePredio() {
        String id = "1";
        PredioDto predioDto = new PredioDto();
        predioDto.setId(id);

        Predio predio = new Predio();
        predio.setId(id);

        when(predioService.existsById(id)).thenReturn(true);
        when(predioMapper.toEntity(predioDto)).thenReturn(predio);
        when(predioService.updatePredio(id, predio)).thenReturn(predio);
        when(predioMapper.toDto(predio)).thenReturn(predioDto);

        ResponseEntity<PredioDto> response = predioController.updatePredio(id, predioDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(predioDto, response.getBody());
    }

    @Test
    void testDeletePredio() {
        String id = "1";

        when(predioService.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = predioController.deletePredio(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}