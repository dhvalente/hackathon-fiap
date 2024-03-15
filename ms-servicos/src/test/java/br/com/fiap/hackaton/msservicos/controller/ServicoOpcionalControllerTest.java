package br.com.fiap.hackaton.msservicos.controller;

import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalRequest;
import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalResponse;
import br.com.fiap.hackaton.msservicos.entity.ServicoOpcional;
import br.com.fiap.hackaton.msservicos.enums.TipoServicosOpcionais;
import br.com.fiap.hackaton.msservicos.repository.ServicoOpcionalRepository;
import br.com.fiap.hackaton.msservicos.service.IServicoOpcionalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ServicoOpcionalControllerTest {

    // Para efetuar as requisições
    private MockMvc mockMvc;


    @Mock
    private IServicoOpcionalService opcionalService;

    AutoCloseable mock;

    ServicoOpcional servicoOpcional;
    ServicoOpcionalRequest servicoEOpcionalRequest;
    ServicoOpcionalResponse servicoEOpcionalResponse;

    // Antes de cada teste iniciar
    @BeforeEach
    void setup(){

        mock = MockitoAnnotations.openMocks(this);

        ServicoOpcionalController controller = new ServicoOpcionalController(opcionalService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();


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


    }



    // Após cada teste limpar da memória
    @AfterEach
    void tearDown() throws Exception {
        mock.close();

    }

    @Test
    void devePermitirCadastrar() throws Exception {

            //Arrange

            when(opcionalService.adicionar(servicoEOpcionalRequest))
                    .thenAnswer(i -> i.getArgument(0));
            //Action

            mockMvc.perform(MockMvcRequestBuilders.post("/servicos-opcionais")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(servicoEOpcionalRequest)))
                            .andExpect(status().isCreated());

            // Assert
            verify(opcionalService,times(1)).adicionar(any(ServicoOpcionalRequest.class));


        }


    @Test
    void deveAtualizarRegistro() throws Exception {

        //Arrange
        Long id = Long.valueOf(1);

        servicoEOpcionalRequest.setNome("Chiclete");



        when(opcionalService.atualizar(id,servicoEOpcionalRequest))
                .thenAnswer(i -> i.getArgument(0));


        //Action

        mockMvc.perform(MockMvcRequestBuilders.put("/servicos-opcionais/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(servicoEOpcionalRequest)))
                        .andExpect(status().isOk());

        // Assert
        verify(opcionalService,times(1)).atualizar(any(Long.class),any(ServicoOpcionalRequest.class));


    }



    @Test
    void devePermitirBuscarPorId() throws Exception {


        var id = Long.valueOf(1);

        when(opcionalService.buscarPorId(any(Long.class)))
                .thenReturn(servicoEOpcionalResponse);

        mockMvc.perform(get("/servicos-opcionais/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(opcionalService, times(1))
                .buscarPorId(any(Long.class));


    }

    @Test
    void deveListarTodos() throws Exception {


        Page<ServicoOpcionalResponse> page = new PageImpl<>(Collections.singletonList(
                servicoEOpcionalResponse
        ));

        Pageable pageable = PageRequest.of(0, 10);

        when(opcionalService.listarTodos(pageable))
                .thenReturn(page);


        mockMvc.perform(get("/servicos-opcionais?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is5xxServerError());

        verify(opcionalService, times(1))
                .listarTodos(any(Pageable.class));


    }

    @Test
    void devePermitirBuscarPorNome() throws Exception {

        var lista = Arrays.asList(
                servicoEOpcionalResponse

        );

        String  nome = "Cerveja";

        when(opcionalService.buscarPorNome(any(String.class)))
                .thenReturn(lista);

        mockMvc.perform(get("/servicos-opcionais/nome?nome=Cerveja")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(opcionalService, times(1))
                .buscarPorNome(any(String.class));


    }

    @Test
    void devePermitirExcluirPorId() throws Exception {

        var id = Long.valueOf(1);

        doNothing().when(opcionalService).remover(id);


        mockMvc.perform(delete("/servicos-opcionais/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(opcionalService, times(1))
                .remover(any(Long.class));


    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
