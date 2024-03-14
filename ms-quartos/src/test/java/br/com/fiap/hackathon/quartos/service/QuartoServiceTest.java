package br.com.fiap.hackathon.quartos.service;

import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.repository.QuartoRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

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
  @DisplayName("Test getting all quartos")
  void testGetAllQuartos() {
    when(quartoRepository.findAll()).thenReturn(List.of(new Quarto()));

    List<Quarto> result = quartoService.getAllQuartos();
    Assertions.assertEquals(List.of(new Quarto()), result);
  }

  @Test
  @DisplayName("Test getting quarto by id")
  void testGetQuartoById() {
    when(quartoRepository.findById(any())).thenReturn(Optional.of(new Quarto()));

    Quarto result = quartoService.getQuartoById("id");
    Assertions.assertEquals(new Quarto(), result);
  }

  @Test
  @DisplayName("Test creating a quarto")
  void testCreateQuarto() {
    when(quartoRepository.save(any())).thenReturn(new Quarto());

    Quarto result = quartoService.createQuarto(new Quarto());
    Assertions.assertEquals(new Quarto(), result);
  }

  @Test
  @DisplayName("Test updating a quarto")
  void testUpdateQuarto() {
    when(quartoRepository.save(any())).thenReturn(new Quarto());
    when(quartoRepository.findById(any())).thenReturn(Optional.of(new Quarto()));

    Quarto result = quartoService.updateQuarto("id", new Quarto());
    Assertions.assertEquals(new Quarto(), result);
  }

  @Test
  @DisplayName("Test deleting a quarto")
  void testDeleteQuarto() {
    when(quartoRepository.findById(any())).thenReturn(Optional.of(new Quarto()));

    quartoService.deleteQuarto("id");
  }
}