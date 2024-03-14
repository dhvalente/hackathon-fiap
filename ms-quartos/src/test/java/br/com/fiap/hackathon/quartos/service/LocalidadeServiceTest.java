package br.com.fiap.hackathon.quartos.service;

import br.com.fiap.hackathon.quartos.entity.Localidade;
import br.com.fiap.hackathon.quartos.repository.LocalidadeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.List;

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
  void testGetAllLocalidades() {
    when(localidadeRepository.findAll()).thenReturn(List.of(new Localidade()));

    List<Localidade> result = localidadeService.getAllLocalidades();
    Assertions.assertEquals(List.of(new Localidade()), result);
  }

  @Test
  void testGetLocalidadeById() {
    when(localidadeRepository.findById(any())).thenReturn(null);

    Localidade result = localidadeService.getLocalidadeById("id");
    Assertions.assertEquals(new Localidade(), result);
  }

  @Test
  void testCreateLocalidade() {
    when(localidadeRepository.save(any())).thenReturn(new Localidade());

    Localidade result = localidadeService.createLocalidade(new Localidade());
    Assertions.assertEquals(new Localidade(), result);
  }

  @Test
  void testUpdateLocalidade() {
    when(localidadeRepository.save(any())).thenReturn(new Localidade());
    when(localidadeRepository.findById(any())).thenReturn(null);

    Localidade result = localidadeService.updateLocalidade("id", new Localidade());
    Assertions.assertEquals(new Localidade(), result);
  }

  @Test
  void testDeleteLocalidade() {
    when(localidadeRepository.findById(any())).thenReturn(null);

    localidadeService.deleteLocalidade("id");
  }

  @Test
  void testExistsById() {
    when(localidadeRepository.existsById(any())).thenReturn(true);

    boolean result = localidadeService.existsById("localidadeId");
    Assertions.assertEquals(true, result);
  }
}
