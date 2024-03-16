package br.com.fiap.hackathon.quartos.service;

import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.repository.PredioRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

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
  @DisplayName("Test getting all predios")
  void testGetAllPredios() {
    when(predioRepository.findAll()).thenReturn(List.of(new Predio()));

    List<Predio> result = predioService.getAllPredios();
    Assertions.assertEquals(List.of(new Predio()), result);
  }

  @Test
  @DisplayName("Test getting predio by id")
  void testGetPredioById() {
    when(predioRepository.findById(any())).thenReturn(Optional.of(new Predio()));

    Predio result = predioService.getPredioById("id");
    Assertions.assertEquals(new Predio(), result);
  }

  @Test
  @DisplayName("Test creating a predio")
  void testCreatePredio() {
    when(predioRepository.save(any())).thenReturn(new Predio());

    Predio result = predioService.createPredio(new Predio());
    Assertions.assertEquals(new Predio(), result);
  }

  @Test
  @DisplayName("Test updating a predio")
  void testUpdatePredio() {
    when(predioRepository.save(any())).thenReturn(new Predio());
    when(predioRepository.findById(any())).thenReturn(Optional.of(new Predio()));

    Predio result = predioService.updatePredio("id", new Predio());
    Assertions.assertEquals(new Predio(), result);
  }

  @Test
  @DisplayName("Test deleting a predio")
  void testDeletePredio() {
    when(predioRepository.findById(any())).thenReturn(Optional.of(new Predio()));

    predioService.deletePredio("id");
  }

  @Test
  @DisplayName("Test if predio exists by id")
  void testExistsById() {
    when(predioRepository.existsById(any())).thenReturn(true);

    boolean result = predioService.existsById("predioId");
    Assertions.assertTrue(result);
  }
}