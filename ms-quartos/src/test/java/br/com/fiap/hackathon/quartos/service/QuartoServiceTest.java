package br.com.fiap.hackathon.quartos.service;

import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.repository.QuartoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.List;

import static org.mockito.Mockito.*;

class QuartoServiceTest {
  @Mock QuartoRepository quartoRepository;
  @Mock Logger log;
  @InjectMocks QuartoService quartoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllQuartos() {
    when(quartoRepository.findAll()).thenReturn(List.of(new Quarto()));

    List<Quarto> result = quartoService.getAllQuartos();
    Assertions.assertEquals(List.of(new Quarto()), result);
  }

  @Test
  void testGetQuartoById() {
    when(quartoRepository.findById(any())).thenReturn(null);

    Quarto result = quartoService.getQuartoById("id");
    Assertions.assertEquals(new Quarto(), result);
  }

  @Test
  void testCreateQuarto() {
    when(quartoRepository.save(any())).thenReturn(new Quarto());

    Quarto result = quartoService.createQuarto(new Quarto());
    Assertions.assertEquals(new Quarto(), result);
  }

  @Test
  void testUpdateQuarto() {
    when(quartoRepository.save(any())).thenReturn(new Quarto());
    when(quartoRepository.findById(any())).thenReturn(null);

    Quarto result = quartoService.updateQuarto("id", new Quarto());
    Assertions.assertEquals(new Quarto(), result);
  }

  @Test
  void testDeleteQuarto() {
    when(quartoRepository.findById(any())).thenReturn(null);

    quartoService.deleteQuarto("id");
  }
}

// Generated with love by TestMe :) Please raise issues & feature requests at:
// https://weirddev.com/forum#!/testme
