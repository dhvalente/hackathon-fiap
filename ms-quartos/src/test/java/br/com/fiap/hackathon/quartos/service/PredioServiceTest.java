package br.com.fiap.hackathon.quartos.service;

import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.repository.PredioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.List;

import static org.mockito.Mockito.*;

class PredioServiceTest {
  @Mock PredioRepository predioRepository;
  @Mock Logger log;
  @InjectMocks PredioService predioService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllPredios() {
    when(predioRepository.findAll()).thenReturn(List.of(new Predio()));

    List<Predio> result = predioService.getAllPredios();
    Assertions.assertEquals(List.of(new Predio()), result);
  }

  @Test
  void testGetPredioById() {
    when(predioRepository.findById(any())).thenReturn(null);

    Predio result = predioService.getPredioById("id");
    Assertions.assertEquals(new Predio(), result);
  }

  @Test
  void testCreatePredio() {
    when(predioRepository.save(any())).thenReturn(new Predio());

    Predio result = predioService.createPredio(new Predio());
    Assertions.assertEquals(new Predio(), result);
  }

  @Test
  void testUpdatePredio() {
    when(predioRepository.save(any())).thenReturn(new Predio());
    when(predioRepository.findById(any())).thenReturn(null);

    Predio result = predioService.updatePredio("id", new Predio());
    Assertions.assertEquals(new Predio(), result);
  }

  @Test
  void testDeletePredio() {
    when(predioRepository.findById(any())).thenReturn(null);

    predioService.deletePredio("id");
  }

  @Test
  void testExistsById() {
    when(predioRepository.existsById(any())).thenReturn(true);

    boolean result = predioService.existsById("predioId");
    Assertions.assertEquals(true, result);
  }
}

// Generated with love by TestMe :) Please raise issues & feature requests at:
// https://weirddev.com/forum#!/testme
