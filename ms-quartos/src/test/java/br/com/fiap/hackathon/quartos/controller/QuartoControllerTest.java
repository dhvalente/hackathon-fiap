package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.mappers.QuartoMapper;
import br.com.fiap.hackathon.quartos.service.PredioService;
import br.com.fiap.hackathon.quartos.service.QuartoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    void testGetAllQuartos(){
        when(quartoService.getAllQuartos()).thenReturn(List.of(new Quarto()));

        ResponseEntity<List<QuartoDto>> result = quartoController.getAllQuartos();
        Assertions.assertEquals(new ResponseEntity<List<QuartoDto>>(List.of(new QuartoDto()), null, 0), result);
    }

    @Test
    void testGetQuartoById(){
        when(quartoService.getQuartoById(anyString())).thenReturn(new Quarto());
        when(quartoMapper.toDto(any())).thenReturn(new QuartoDto());

        ResponseEntity<QuartoDto> result = quartoController.getQuartoById("id");
        Assertions.assertEquals(new ResponseEntity<QuartoDto>(new QuartoDto(), null, 0), result);
    }

    @Test
    void testCreateQuarto(){
        when(quartoService.createQuarto(any())).thenReturn(new Quarto());
        when(quartoMapper.toDto(any())).thenReturn(new QuartoDto());
        when(quartoMapper.toEntity(any())).thenReturn(new Quarto());
        when(predioService.existsById(anyString())).thenReturn(true);

        ResponseEntity<QuartoDto> result = quartoController.createQuarto(new QuartoDto());
        Assertions.assertEquals(new ResponseEntity<QuartoDto>(new QuartoDto(), null, 0), result);
    }

    @Test
    void testUpdateQuarto(){
        when(quartoService.updateQuarto(anyString(), any())).thenReturn(new Quarto());
        when(quartoMapper.toDto(any())).thenReturn(new QuartoDto());
        when(quartoMapper.toEntity(any())).thenReturn(new Quarto());

        ResponseEntity<QuartoDto> result = quartoController.updateQuarto("id", new QuartoDto());
        Assertions.assertEquals(new ResponseEntity<QuartoDto>(new QuartoDto(), null, 0), result);
    }

    @Test
    void testDeleteQuarto(){
        ResponseEntity<Void> result = quartoController.deleteQuarto("id");
        Assertions.assertEquals(new ResponseEntity<Void>(null, null, 0), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme