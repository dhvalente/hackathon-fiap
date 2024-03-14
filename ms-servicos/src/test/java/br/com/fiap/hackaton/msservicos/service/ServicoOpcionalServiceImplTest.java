package br.com.fiap.hackaton.msservicos.service;

import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalRequest;
import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalResponse;
import br.com.fiap.hackaton.msservicos.entity.ServicoOpcional;
import br.com.fiap.hackaton.msservicos.enums.TipoServicosOpcionais;
import br.com.fiap.hackaton.msservicos.repository.ServicoOpcionalRepository;
import br.com.fiap.hackaton.msservicos.service.impl.ServicoOpcionalServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.*;

public class ServicoOpcionalServiceImplTest {

    // Trabalhar com Fake - Mockito.

    AutoCloseable mock;
    ServicoOpcional servicoOpcional;
    ServicoOpcionalRequest  servicoEOpcionalRequest;
    ServicoOpcionalResponse servicoEOpcionalResponse;


    private ServicoOpcionalServiceImpl service;
    @Mock
    private ServicoOpcionalRepository repository;

    @BeforeEach  // antes de cada teste
    void setup(){

        Long id = Long.valueOf(1);

        servicoEOpcionalRequest = new ServicoOpcionalRequest();
        servicoEOpcionalRequest.setNome("Cerveja");
        servicoEOpcionalRequest.setTipo(TipoServicosOpcionais.ITEM);
        servicoEOpcionalRequest.setValor(5d);

        servicoOpcional = new ServicoOpcional();
        servicoOpcional.setNome("Cerveja");
        servicoOpcional.setTipo(TipoServicosOpcionais.ITEM);
        servicoOpcional.setValor(5d);
        servicoOpcional.setId(id);

        servicoEOpcionalResponse = new ServicoOpcionalResponse(servicoOpcional);

        mock = MockitoAnnotations.openMocks(this);
        service = new ServicoOpcionalServiceImpl(repository);
    }

    // Limpar o mock da memoria.
    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }



    @Test
    void DevelistarTodos() {

        // Arrange
        Page<ServicoOpcional> lista = new  org.springframework.data.domain.PageImpl<>(Arrays.asList(
                servicoOpcional

        ));
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(lista);

        // Action
        var resultadoObtido = service.listarTodos(Pageable.unpaged());


        //Assert
        verify(repository,times(1)).findAll(any(Pageable.class));


    }

    @Test
    void DeveAdicionar(){

        // conceito do triple A
        // Arrange esta no before each

        // Mockito
        when(repository.save(any(ServicoOpcional.class)))
                .thenAnswer( i -> i.getArgument(0));

        // Act
        var registrado = service.adicionar(servicoEOpcionalRequest);

        // Assert

        assertThat(registrado).isInstanceOf(ServicoOpcionalResponse.class).isNotNull();
        assertThat(registrado.getNome()).isEqualTo(servicoEOpcionalRequest.getNome());
        assertThat(registrado.getValor()).isEqualTo(servicoEOpcionalRequest.getValor());
        verify(repository,times(1)).save(any(ServicoOpcional.class));

    }

    @Test
    void DeveAtualizar(){



        // conceito do triple A
        // Parte do Arrange esta no before each

        Long id = Long.valueOf(1);
        servicoEOpcionalRequest.setNome("Chiclete");
        servicoEOpcionalResponse.setNome("Chiclete");


        // Mockito
        when(repository.findById(id)).thenReturn(Optional.of(servicoOpcional));

        when(repository.save(any(ServicoOpcional.class)))
                .thenAnswer( i -> i.getArgument(0));

        // Act
        var registrado = service.atualizar(id,servicoEOpcionalRequest);

        // Assert

        assertThat(registrado).isInstanceOf(ServicoOpcionalResponse.class).isNotNull();
        assertThat(registrado.getNome()).isEqualTo(servicoEOpcionalRequest.getNome());
        assertThat(registrado.getValor()).isEqualTo(servicoEOpcionalRequest.getValor());
        verify(repository,times(1)).save(any(ServicoOpcional.class));
        verify(repository,times(1)).findById(any(Long.class));

    }


    @Test
    void remover(){

        // Arrange
        var id = Long.valueOf(1);

        when(repository.findById(id)).thenReturn(Optional.of(servicoOpcional));
        doNothing().when(repository).deleteById(id);
        //Act
        service.remover(id);
        //Assert

        verify(repository,times(1)).findById(any(Long.class));
        verify(repository,times(1)).deleteById(any(Long.class));


    }

    @Test
    void DeveBuscarPorId(){


        var id = Long.valueOf(1);

        servicoOpcional.setId(id);

        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(servicoOpcional)); /// mockado \ fake o retorno.

        // Act
        var resultado = service.buscarPorId(id);

        //Assert

        verify(repository,times(1)).findById(any(Long.class));



    }

    @Test
    void DeveBuscarPorNome(){
        String nome = "Cerveja";

        var lista = Arrays.asList(
                servicoOpcional

        );

        when(repository.findByNomeLike(any(String.class)))
                .thenReturn(lista); /// mockado \ fake o retorno.

        // Act
        var resultado = service.buscarPorNome(nome);

        //Assert

        verify(repository,times(1)).findByNomeLike(any(String.class));

    }


}
