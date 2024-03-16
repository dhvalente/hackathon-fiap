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
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class QuartoControllerTest {
    @Mock
    QuartoService quartoService;
    @Mock
    QuartoMapper quartoMapper;
    @Mock
    PredioService predioService;

    @Mock
    LocalidadeService localidadeService;
    @InjectMocks
    QuartoController quartoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllQuartos() {
        List<Quarto> quartos = Collections.singletonList(new Quarto());

        List<QuartoDto> quartoDtos = Collections.singletonList(new QuartoDto());

        when(quartoService.getAllQuartos()).thenReturn(quartos);
        when(quartoMapper.toDto(any())).thenReturn(new QuartoDto());

        ResponseEntity<List<QuartoDto>> response = quartoController.getAllQuartos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quartoDtos, response.getBody());
    }

    @Test
    void testGetQuartoById() {
        String id = "1";

        Quarto quarto = new Quarto();
        QuartoDto quartoDto = new QuartoDto();

        when(quartoService.existsById(id)).thenReturn(true);
        when(quartoService.getQuartoById(id)).thenReturn(quarto);
        when(quartoMapper.toDto(quarto)).thenReturn(quartoDto);

        ResponseEntity<QuartoDto> response = quartoController.getQuartoById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quartoDto, response.getBody());
    }

    @Test
    void testCreateQuarto() {
        QuartoDto quartoDto = new QuartoDto();
        quartoDto.setId("1");
        quartoDto.setPredioId("2");

        Predio predio = new Predio();
        predio.setId("2");
        predio.setLocalidadeId("3");

        Localidade localidade = new Localidade();
        localidade.setId("3");
        localidade.setPredios(new ArrayList<>());
        localidade.getPredios().add(predio);

        Quarto quarto = new Quarto();
        quarto.setId("1");

        when(predioService.existsById(quartoDto.getPredioId())).thenReturn(true);
        when(predioService.getPredioById(quartoDto.getPredioId())).thenReturn(predio);
        when(localidadeService.getLocalidadeById(predio.getLocalidadeId())).thenReturn(localidade);
        when(quartoMapper.toEntity(quartoDto)).thenReturn(quarto);
        when(quartoService.createQuarto(quarto)).thenReturn(quarto);
        when(quartoMapper.toDto(quarto)).thenReturn(quartoDto);

        ResponseEntity<QuartoDto> response = quartoController.createQuarto(quartoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quartoDto, response.getBody());

        verify(quartoService, times(1)).createQuarto(quarto);
        verify(predioService, times(1)).updatePredio(quartoDto.getPredioId(), predio);
        verify(localidadeService, times(1)).updateLocalidade(localidade.getId(), localidade);
    }

    @Test
    void testCreateQuarto_PredioNaoEncontrado() {
        QuartoDto quartoDto = new QuartoDto();
        quartoDto.setPredioId("999");

        when(predioService.existsById(quartoDto.getPredioId())).thenReturn(false);

        assertThrows(GenericException.class, () -> quartoController.createQuarto(quartoDto));
    }

    @Test
    void testCreateQuarto_QuartoExistente() {
        QuartoDto quartoDto = new QuartoDto();
        quartoDto.setId("1");

        when(predioService.existsById(quartoDto.getPredioId())).thenReturn(true);
        when(quartoService.existsById(quartoDto.getId())).thenReturn(true);

        assertThrows(GenericException.class, () -> quartoController.createQuarto(quartoDto));
    }

    @Test
    void testUpdateQuarto() {
        QuartoDto quartoDto = new QuartoDto();
        quartoDto.setId("1");

        Quarto quarto = new Quarto();

        when(quartoService.existsById(quartoDto.getId())).thenReturn(true);
        when(quartoMapper.toEntity(quartoDto)).thenReturn(quarto);
        when(quartoService.updateQuarto(quartoDto.getId(), quarto)).thenReturn(quarto);
        when(quartoMapper.toDto(quarto)).thenReturn(quartoDto);

        ResponseEntity<QuartoDto> response = quartoController.updateQuarto(quartoDto.getId(), quartoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quartoDto, response.getBody());
    }

    @Test
    void testUpdateQuarto_QuartoNaoEncontrado() {
        QuartoDto quartoDto = new QuartoDto();
        quartoDto.setId("999");

        when(quartoService.existsById(quartoDto.getId())).thenReturn(false);

        assertThrows(GenericException.class, () -> quartoController.updateQuarto(quartoDto.getId(), quartoDto));
    }

    @Test
    void testDeleteQuarto() {
        String id = "1";

        when(quartoService.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = quartoController.deleteQuarto(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(quartoService, times(1)).deleteQuarto(id);
    }

    @Test
    void testDeleteQuarto_QuartoNaoEncontrado() {
        String id = "999";

        when(quartoService.existsById(id)).thenReturn(false);

        assertThrows(GenericException.class, () -> quartoController.deleteQuarto(id));
    }

}