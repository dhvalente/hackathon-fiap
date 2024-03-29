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

        when(clienteRecordMock.nome()).thenReturn("Fulano");
        when(clienteRecordMock.sobrenome()).thenReturn("Silva");
        when(clienteRecordMock.cpf()).thenReturn("12345678900");
        when(clienteRecordMock.passaporte()).thenReturn("ABC123");
        when(clienteRecordMock.dataDeAniversario()).thenReturn(LocalDate.of(1990, 1, 1));
        when(clienteRecordMock.genero()).thenReturn(Generos.MASCULINO);
        when(clienteRecordMock.endereco()).thenReturn(new Endereco(1L,"Rua das Flores", "123", "São Paulo", "Centro", "SP", "01234-567"));
        when(clienteRecordMock.paisDeOrigem()).thenReturn("Brasil");
        when(clienteRecordMock.email()).thenReturn("fulano@example.com");
        when(clienteRecordMock.telefone()).thenReturn("123456789");

        assertEquals("Fulano", clienteRecordMock.nome());
        assertEquals("Silva", clienteRecordMock.sobrenome());
        assertEquals("12345678900", clienteRecordMock.cpf());
        assertEquals("ABC123", clienteRecordMock.passaporte());
        assertEquals(LocalDate.of(1990, 1, 1), clienteRecordMock.dataDeAniversario());
        assertEquals(Generos.MASCULINO, clienteRecordMock.genero());

        Cliente cliente = new Cliente(/* mock com os campos preenchidos */);
        when(clienteService.criarCliente(clienteRecordMock)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.createPost(clienteRecordMock);

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

    @Test
    public void testFindById() {

        Cliente clienteMock = new Cliente();
        clienteMock.setId(1L);
        clienteMock.setNome("Teste Cliente");

        when(clienteService.obterClientePorId(1L)).thenReturn(clienteMock);

        ResponseEntity<Cliente> response = clienteController.findById(1L);

        assertEquals(200, response.getStatusCodeValue());

        Cliente clienteRetornado = response.getBody();
        assertEquals(1L, clienteRetornado.getId());
        assertEquals("Teste Cliente", clienteRetornado.getNome());
    }

    @Test
    public void testExcluir() {
        // Configurando o comportamento do clienteService para não fazer nada (void method)
        doNothing().when(clienteService).excluirCliente(1L);

        // Chamando o método excluir do controller
        ResponseEntity<Void> response = clienteController.excluir(1L);

        // Verificando o status da resposta
        assertEquals(204, response.getStatusCodeValue());

        // Verificando se o método excluirCliente do serviço foi chamado corretamente
        verify(clienteService).excluirCliente(1L);
    }


    @Test
    public void testUpdate() {
        // Criando um ClienteRecord com valores fictícios
        ClienteRecord clienteRecord = new ClienteRecord(
                "João",
                "Silva",
                "123.456.789-00",
                "AB123456",
                LocalDate.of(1990, 5, 15),
                Generos.MASCULINO,
                new Endereco(1L,"Rua das Flores", "123", "São Paulo", "Centro", "SP", "01234-567"),
                "Brasil",
                "joao@example.com",
                "123456789"
        );

        // Mock do cliente retornado pelo serviço após a atualização
        Cliente clienteAtualizado = new Cliente(
                1L,
                "João",
                "Silva",
                "123.456.789-00",
                "AB123456",
                LocalDate.of(1990, 5, 15),
                Generos.MASCULINO,
                new Endereco(1L,"Rua das Flores", "123", "São Paulo", "Centro", "SP", "01234-567"),
                "Brasil",
                "123456789",
                "joao@example.com"
        );

        // Configurando o comportamento do clienteService
        when(clienteService.atualizarCliente(1L, clienteRecord)).thenReturn(clienteAtualizado);

        // Chamando o método update do controller
        ResponseEntity<Cliente> response = clienteController.update(1L, clienteRecord);

        // Verificando o status da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificando se o cliente retornado está correto
        Cliente clienteRetornado = response.getBody();
        assertEquals(1L, clienteRetornado.getId());
        assertEquals("João", clienteRetornado.getNome());
        assertEquals("Silva", clienteRetornado.getSobrenome());
        assertEquals("123.456.789-00", clienteRetornado.getCpf());
        assertEquals("AB123456", clienteRetornado.getPassaporte());
        assertEquals(LocalDate.of(1990, 5, 15), clienteRetornado.getDataDeAniversario());
        assertEquals(Generos.MASCULINO, clienteRetornado.getGenero());
        assertEquals("Brasil", clienteRetornado.getPaisDeOrigem());
        assertEquals("joao@example.com", clienteRetornado.getEmail());
        assertEquals("123456789", clienteRetornado.getTelefone());
    }
}