package br.com.fiap.hackaton.msservicos.repository;

import br.com.fiap.hackaton.msservicos.entity.ServicoOpcional;
import br.com.fiap.hackaton.msservicos.enums.TipoServicosOpcionais;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

public class ServicoOpcionalRepositoryTest {

    // Mockito que cria os Fakes
    @Mock
    private ServicoOpcionalRepository repository;

    AutoCloseable openMocks;
    ServicoOpcional servicoEOpcional;
    ServicoOpcional servicoEOpcional2;

    /// Antes de cada execução, cria um novo mock , fake, ou seja inicia todas as variaves que tem a anotação Mock
    @BeforeEach
    void setup(){
        // Arrange prepara



        servicoEOpcional = new ServicoOpcional();
        servicoEOpcional.setNome("Cerveja");
        servicoEOpcional.setTipo(TipoServicosOpcionais.ITEM);
        servicoEOpcional.setValor(5d);
        servicoEOpcional.setId(Long.valueOf(1));

        servicoEOpcional2 = new ServicoOpcional();
        servicoEOpcional2.setNome("Massagem");
        servicoEOpcional2.setTipo(TipoServicosOpcionais.SERVICO);
        servicoEOpcional2.setValor(100d);
        servicoEOpcional.setId(Long.valueOf(2));

        openMocks = MockitoAnnotations.openMocks(this);
    }

    // Após cada execução...
    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarServicoOpcional(){
         //Triple A - Esta no before each

        when(repository.save(any(ServicoOpcional.class))).thenReturn(servicoEOpcional);

        //Act -- executa
        var mensagemArmazenada = repository.save(servicoEOpcional);

        // Assert -- verifica
         assertThat(mensagemArmazenada).isNotNull().isEqualTo(servicoEOpcional);

        // garanto que tive uma chamada a banco de dados...  do mockito... no sabe para deixa generico poderia usar Mensagem.class com Any()
        // e o que chamamos de spy

        verify(repository, times(1)).save(any(ServicoOpcional.class));

    }

    @Test
    void devePermitirRemoverServicoOpcional(){

        // Arrange
         var id = servicoEOpcional.getId();
        // metodo do Mockito....
        doNothing().when(repository).deleteById(id);
        // Act
        repository.deleteById(id);
        //Assert
        verify(repository,times(1)).deleteById(id);

    }


    @Test
    void devePermitirBuscarServicoOpcional(){

        //arrange
        var id = servicoEOpcional.getId();

        when(repository.findById(id))
                .thenReturn(Optional.of(servicoEOpcional));

        // act
        var servicoEOpcionalRecebida = repository.findById(id);

        // Assert

        assertThat(servicoEOpcionalRecebida)
                .isPresent();

        servicoEOpcionalRecebida.ifPresent( recebida -> {
                    assertThat(recebida.getId()).isEqualTo(servicoEOpcional.getId());
                    assertThat(recebida.getNome()).isEqualTo(servicoEOpcional.getNome());

                }
        );

        verify(repository,times(1)).findById(id);

    }


    @Test
    void devePermitirListarServicoOpcional(){

        // Arrange

        var lista = Arrays.asList(
                servicoEOpcional,
                servicoEOpcional2
        );

        when(repository.findAll()).thenReturn(lista);

        // Act
        var recebidos = repository.findAll(Pageable.unpaged());

        // Assert
        assertThat(lista)
                .hasSize(2);

        verify(repository,times(1)).findAll(any(Pageable.class));


    }


    @Test
    void devePermitirBuscarServicoOpcionalPorNome(){

        //arrange
       String nome  = "Cerveja";

        var lista = Arrays.asList(
                servicoEOpcional
        );

        when(repository.findByNomeLike(nome))
                .thenReturn(lista);

        // act
        var servicoEOpcionalRecebida = repository.findByNomeLike(nome);

        // Assert
        assertThat(lista)
                .hasSize(1)
                .containsExactlyInAnyOrder(servicoEOpcional);


        verify(repository,times(1)).findByNomeLike(nome);

    }



}
