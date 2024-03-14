package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.PredioDto;
import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.mappers.PredioMapper;
import br.com.fiap.hackathon.quartos.service.LocalidadeService;
import br.com.fiap.hackathon.quartos.service.PredioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    void testGetAllPredios(){
        when(predioService.getAllPredios()).thenReturn(List.of(new Predio()));

        ResponseEntity<List<PredioDto>> result = predioController.getAllPredios();
        Assertions.assertEquals(new ResponseEntity<List<PredioDto>>(List.of(new PredioDto()), null, 0), result);
    }

    @Test
    void testGetPredioById(){
        when(predioService.getPredioById(anyString())).thenReturn(new Predio());
        when(predioMapper.toDto(any())).thenReturn(new PredioDto());

        ResponseEntity<PredioDto> result = predioController.getPredioById("id");
        Assertions.assertEquals(new ResponseEntity<PredioDto>(new PredioDto(), null, 0), result);
    }

    @Test
    void testCreatePredio(){
        when(predioService.createPredio(any())).thenReturn(new Predio());
        when(predioMapper.toDto(any())).thenReturn(new PredioDto());
        when(predioMapper.toEntity(any())).thenReturn(new Predio());
        when(localidadeService.existsById(anyString())).thenReturn(true);

        ResponseEntity<PredioDto> result = predioController.createPredio(new PredioDto());
        Assertions.assertEquals(new ResponseEntity<PredioDto>(new PredioDto(), null, 0), result);
    }

    @Test
    void testUpdatePredio(){
        when(predioService.updatePredio(anyString(), any())).thenReturn(new Predio());
        when(predioMapper.toDto(any())).thenReturn(new PredioDto());
        when(predioMapper.toEntity(any())).thenReturn(new Predio());

        ResponseEntity<PredioDto> result = predioController.updatePredio("id", new PredioDto());
        Assertions.assertEquals(new ResponseEntity<PredioDto>(new PredioDto(), null, 0), result);
    }

    @Test
    void testDeletePredio(){
        ResponseEntity<Void> result = predioController.deletePredio("id");
        Assertions.assertEquals(new ResponseEntity<Void>(null, null, 0), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme