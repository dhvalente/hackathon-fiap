package br.com.fiap.hackathon.quartos.controller;

import br.com.fiap.hackathon.quartos.Exception.GenericException;
import br.com.fiap.hackathon.quartos.dtos.LocalidadeDto;
import br.com.fiap.hackathon.quartos.entity.Localidade;
import br.com.fiap.hackathon.quartos.mappers.LocalidadeMapper;
import br.com.fiap.hackathon.quartos.mappers.PredioMapper;
import br.com.fiap.hackathon.quartos.mappers.QuartoMapper;
import br.com.fiap.hackathon.quartos.service.LocalidadeService;
import br.com.fiap.hackathon.quartos.service.PredioService;
import br.com.fiap.hackathon.quartos.service.QuartoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LocalidadeControllerTest {
  @Mock LocalidadeService localidadeService;
  @Mock LocalidadeMapper localidadeMapper;
  @Mock PredioService predioService;
  @Mock PredioMapper predioMapper;
  @Mock QuartoService quartoService;
  @Mock QuartoMapper quartoMapper;
  @InjectMocks LocalidadeController localidadeController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Testa a obtenção de todas as localidades")
  void testGetAllLocalidades() {
    Localidade localidade = new Localidade();
    LocalidadeDto localidadeDto = new LocalidadeDto();

    when(localidadeService.getAllLocalidades()).thenReturn(Collections.singletonList(localidade));
    when(localidadeMapper.toDto(localidade)).thenReturn(localidadeDto);

    ResponseEntity<List<LocalidadeDto>> response = localidadeController.getAllLocalidades();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Collections.singletonList(localidadeDto), response.getBody());
  }

  @Test
  @DisplayName("Testa a obtenção de uma localidade pelo ID")
  void testGetLocalidadeById() {
    String id = "1";
    Localidade localidade = new Localidade();
    LocalidadeDto localidadeDto = new LocalidadeDto();

    when(localidadeService.existsById(id)).thenReturn(true);
    when(localidadeService.getLocalidadeById(id)).thenReturn(localidade);
    when(localidadeMapper.toDto(localidade)).thenReturn(localidadeDto);

    ResponseEntity<LocalidadeDto> response = localidadeController.getLocalidadeById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(localidadeDto, response.getBody());
  }

  @Test
  @DisplayName("Testa o erro de Id da localidade não encontrado")
  void testGetLocalidadeById_NotFound() {
    String id = "1";

    when(localidadeService.existsById(id)).thenReturn(false);

    GenericException exception = assertThrows(GenericException.class,
            () -> localidadeController.getLocalidadeById(id));

    assertEquals("Não existe uma localidade cadastrada com esse ID: " + id + "! não é possível continuar!",
            exception.getMessage());
  }

  @Test
  @DisplayName("Testa a criação de uma localidade")
  void testCreateLocalidade() {
    LocalidadeDto localidadeDto = new LocalidadeDto();
    localidadeDto.setId("1");

    Localidade localidade = new Localidade();

    when(localidadeService.existsById(localidadeDto.getId())).thenReturn(false);
    when(localidadeMapper.toEntity(localidadeDto)).thenReturn(localidade);
    when(localidadeService.createLocalidade(localidade)).thenReturn(localidade);
    when(localidadeMapper.toDto(localidade)).thenReturn(localidadeDto);

    ResponseEntity<LocalidadeDto> response = localidadeController.createLocalidade(localidadeDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(localidadeDto, response.getBody());
  }

  @Test
  @DisplayName("Testa a atualização de uma localidade")
  void testUpdateLocalidade() {
    String id = "1";
    LocalidadeDto localidadeDto = new LocalidadeDto();
    localidadeDto.setId(id);

    Localidade localidade = new Localidade();
    localidade.setId(id);

    when(localidadeService.existsById(id)).thenReturn(true);
    when(localidadeMapper.toEntity(localidadeDto)).thenReturn(localidade);
    when(localidadeService.updateLocalidade(id, localidade)).thenReturn(localidade);
    when(localidadeMapper.toDto(localidade)).thenReturn(localidadeDto);

    ResponseEntity<LocalidadeDto> response = localidadeController.updateLocalidade(id, localidadeDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(localidadeDto, response.getBody());
  }

  @Test
  @DisplayName("Testa o erro de Id da localidade não encontrado no update")
  void testUpdateLocalidade_NonExistentId() {
    String id = "1";
    LocalidadeDto localidadeDto = new LocalidadeDto();
    localidadeDto.setId(id);

    when(localidadeService.existsById(id)).thenReturn(false);

    GenericException exception = assertThrows(GenericException.class,
            () -> localidadeController.updateLocalidade(id, localidadeDto));

    assertEquals("Não existe uma localidade cadastrada com esse ID: " + id + "! não é possível continuar!",
            exception.getMessage());

    verify(localidadeService, never()).updateLocalidade(id, new Localidade());
  }

  @Test
  @DisplayName("Testa a deleção de uma localidade")
  void testDeleteLocalidade() {
    String id = "1";

    when(localidadeService.existsById(id)).thenReturn(true);

    ResponseEntity<Void> response = localidadeController.deleteLocalidade(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    verify(localidadeService, times(1)).deleteLocalidade(id);
  }
}
