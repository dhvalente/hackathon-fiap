package br.com.fiap.hackathon.quartos.service;

import br.com.fiap.hackathon.quartos.entity.Localidade;
import br.com.fiap.hackathon.quartos.repository.LocalidadeRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class LocalidadeServiceTest {
  @Mock LocalidadeRepository localidadeRepository;
  @Mock Logger log;
  @InjectMocks LocalidadeService localidadeService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test getting all localidades")
  void testGetAllLocalidades() {
    when(localidadeRepository.findAll()).thenReturn(List.of(new Localidade()));

    List<Localidade> result = localidadeService.getAllLocalidades();
    Assertions.assertEquals(List.of(new Localidade()), result);
  }

  @Test
  @DisplayName("Test getting localidade by id")
  void testGetLocalidadeById() {
    when(localidadeRepository.findById(any())).thenReturn(Optional.of(new Localidade()));

    Localidade result = localidadeService.getLocalidadeById("id");
    Assertions.assertEquals(new Localidade(), result);
  }

  @Test
  @DisplayName("Test creating a localidade")
  void testCreateLocalidade() {
    when(localidadeRepository.save(any())).thenReturn(new Localidade());

    Localidade result = localidadeService.createLocalidade(new Localidade());
    Assertions.assertEquals(new Localidade(), result);
  }

  @Test
  @DisplayName("Test updating a localidade")
  void testUpdateLocalidade() {
    when(localidadeRepository.save(any())).thenReturn(new Localidade());
    when(localidadeRepository.findById(any())).thenReturn(Optional.of(new Localidade()));

    Localidade result = localidadeService.updateLocalidade("id", new Localidade());
    Assertions.assertEquals(new Localidade(), result);
  }

  @Test
  @DisplayName("Test deleting a localidade")
  void testDeleteLocalidade() {
    when(localidadeRepository.findById(any())).thenReturn(Optional.of(new Localidade()));

    localidadeService.deleteLocalidade("id");
  }

  @Test
  @DisplayName("Test if localidade exists by id")
  void testExistsById() {
    when(localidadeRepository.existsById(any())).thenReturn(true);

    boolean result = localidadeService.existsById("localidadeId");
    Assertions.assertTrue(result);
  }
}