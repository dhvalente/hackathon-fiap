package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.dtos.LocalidadeDto;
import br.com.fiap.hackathon.quartos.entity.Localidade;
import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.mappers.LocalidadeMapper;
import br.com.fiap.hackathon.quartos.mappers.PredioMapper;
import br.com.fiap.hackathon.quartos.mappers.QuartoMapper;
import br.com.fiap.hackathon.quartos.service.LocalidadeService;
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
class LocalidadeControllerTest {
    @Mock
    LocalidadeService localidadeService;
    @Mock
    LocalidadeMapper localidadeMapper;
    @Mock
    PredioService predioService;
    @Mock
    PredioMapper predioMapper;
    @Mock
    QuartoService quartoService;
    @Mock
    QuartoMapper quartoMapper;
    @InjectMocks
    LocalidadeController localidadeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLocalidades(){
        when(localidadeService.getAllLocalidades()).thenReturn(List.of(new Localidade()));
        when(localidadeMapper.toDto(any())).thenReturn(new LocalidadeDto());

        ResponseEntity<List<LocalidadeDto>> result = localidadeController.getAllLocalidades();
        Assertions.assertEquals(new ResponseEntity<List<LocalidadeDto>>(List.of(new LocalidadeDto()), null, 0), result);
    }

    @Test
    void testGetLocalidadeById(){
        when(localidadeService.getLocalidadeById(anyString())).thenReturn(new Localidade());
        when(localidadeMapper.toDto(any())).thenReturn(new LocalidadeDto());

        ResponseEntity<LocalidadeDto> result = localidadeController.getLocalidadeById("id");
        Assertions.assertEquals(new ResponseEntity<LocalidadeDto>(new LocalidadeDto(), null, 0), result);
    }

    @Test
    void testCreateLocalidade(){
        when(localidadeService.createLocalidade(any())).thenReturn(new Localidade());
        when(localidadeMapper.toDto(any())).thenReturn(new LocalidadeDto());
        when(localidadeMapper.toEntity(any())).thenReturn(new Localidade());
        when(predioService.createPredio(any())).thenReturn(new Predio());
        when(predioMapper.toEntity(any())).thenReturn(new Predio());
        when(quartoService.createQuarto(any())).thenReturn(new Quarto());
        when(quartoMapper.toEntity(any())).thenReturn(new Quarto());

        ResponseEntity<LocalidadeDto> result = localidadeController.createLocalidade(new LocalidadeDto());
        Assertions.assertEquals(new ResponseEntity<LocalidadeDto>(new LocalidadeDto(), null, 0), result);
    }

    @Test
    void testUpdateLocalidade(){
        when(localidadeService.updateLocalidade(anyString(), any())).thenReturn(new Localidade());
        when(localidadeMapper.toDto(any())).thenReturn(new LocalidadeDto());
        when(localidadeMapper.toEntity(any())).thenReturn(new Localidade());
        when(predioService.updatePredio(anyString(), any())).thenReturn(new Predio());
        when(predioMapper.toEntity(any())).thenReturn(new Predio());
        when(quartoService.updateQuarto(anyString(), any())).thenReturn(new Quarto());
        when(quartoMapper.toEntity(any())).thenReturn(new Quarto());

        ResponseEntity<LocalidadeDto> result = localidadeController.updateLocalidade("id", new LocalidadeDto());
        Assertions.assertEquals(new ResponseEntity<LocalidadeDto>(new LocalidadeDto(), null, 0), result);
    }

    @Test
    void testDeleteLocalidade(){
        ResponseEntity<Void> result = localidadeController.deleteLocalidade("id");
        Assertions.assertEquals(new ResponseEntity<Void>(null, null, 0), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme