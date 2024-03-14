package br.com.fiap.hackaton.customer.controller;

import br.com.fiap.hackaton.customer.entity.Cliente;
import br.com.fiap.hackaton.customer.entity.Endereco;
import br.com.fiap.hackaton.customer.enums.Generos;
import br.com.fiap.hackaton.customer.records.ClienteRecord;
import br.com.fiap.hackaton.customer.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost() {
        ClienteRecord clienteRecordMock = mock(ClienteRecord.class);

        // Definindo comportamento esperado
        when(clienteRecordMock.nome()).thenReturn("Fulano");
        when(clienteRecordMock.sobrenome()).thenReturn("Silva");
        when(clienteRecordMock.cpf()).thenReturn("12345678900");
        when(clienteRecordMock.passaporte()).thenReturn("ABC123");
        when(clienteRecordMock.dataDeAniversario()).thenReturn(LocalDate.of(1990, 1, 1));
        when(clienteRecordMock.genero()).thenReturn(Generos.MASCULINO);
        when(clienteRecordMock.endereco()).thenReturn(new Endereco(/* Preencha os campos do Endereco conforme necessário */));
        when(clienteRecordMock.paisDeOrigem()).thenReturn("Brasil");
        when(clienteRecordMock.email()).thenReturn("fulano@example.com");
        when(clienteRecordMock.telefone()).thenReturn("123456789");

        // Teste
        assertEquals("Fulano", clienteRecordMock.nome());
        assertEquals("Silva", clienteRecordMock.sobrenome());
        assertEquals("12345678900", clienteRecordMock.cpf());
        assertEquals("ABC123", clienteRecordMock.passaporte());
        assertEquals(LocalDate.of(1990, 1, 1), clienteRecordMock.dataDeAniversario());
        assertEquals(Generos.MASCULINO, clienteRecordMock.genero());

        Cliente cliente = new Cliente(/* mock com os campos preenchidos */);
        when(clienteService.criarCliente(clienteRecordMock)).thenReturn(cliente);

        // Testing
        ResponseEntity<Cliente> response = clienteController.createPost(clienteRecordMock);

        // Verification
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    public void testFindAll() {
        // Mocking
        Page<ClienteRecord> page = mock(Page.class);
        when(clienteService.obterTodosClientes(any())).thenReturn(page);

        // Testing
        ResponseEntity<Page<ClienteRecord>> response = clienteController.findAll(0, 10);

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }

}