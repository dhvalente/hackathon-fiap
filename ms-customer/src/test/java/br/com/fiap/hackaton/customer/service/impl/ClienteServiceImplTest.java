package br.com.fiap.hackaton.customer.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import br.com.fiap.hackaton.customer.entity.Cliente;
import br.com.fiap.hackaton.customer.enums.Generos;
import br.com.fiap.hackaton.customer.exceptions.ClienteNaoEncontradoException;
import br.com.fiap.hackaton.customer.records.ClienteRecord;
import br.com.fiap.hackaton.customer.repository.ClienteRepository;
import br.com.fiap.hackaton.customer.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCriarCliente() {
        // Mock dos dados do cliente
        ClienteRecord clienteRecord = new ClienteRecord("João", "Silva", "123.456.789-00", "AB123456",
                LocalDate.of(1990, 5, 15), Generos.MASCULINO, null, "Brasil", "joao@example.com", "123456789");

        // Mock do cliente criado
        Cliente clienteCriado = new Cliente();
        clienteCriado.setId(1L);
        clienteCriado.setNome("João");
        clienteCriado.setSobrenome("Silva");

        // Mock do método save do clienteRepository
        when(enderecoRepository.save(any())).thenReturn(null);
        when(clienteRepository.save(any())).thenReturn(clienteCriado);

        // Teste do método criarCliente
        Cliente clienteRetornado = clienteService.criarCliente(clienteRecord);

        // Verificações
        assertNotNull(clienteRetornado);
        assertEquals(1L, clienteRetornado.getId());
        assertEquals("João", clienteRetornado.getNome());
        assertEquals("Silva", clienteRetornado.getSobrenome());
    }

    @Test
    void testObterTodosClientes() {
        // Mock da lista de clientes
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1L, "João", "Silva", "123.456.789-00", "AB123456",
                LocalDate.of(1990, 5, 15), Generos.MASCULINO, null, "Brasil", "joao@example.com", "123456789"));

        // Mock do retorno do clienteRepository.findAll
        Page<Cliente> clientesPage = new PageImpl<>(clientes);
        when(clienteRepository.findAll(any(Pageable.class))).thenReturn(clientesPage);
        Pageable pageable = PageRequest.of(0, 10);
        // Teste do método obterTodosClientes
        Page<ClienteRecord> clientesRetornados = clienteService.obterTodosClientes(pageable);

        // Verificações
        assertEquals(1, clientesRetornados.getContent().size());
        ClienteRecord clienteRetornado = clientesRetornados.getContent().get(0);
        assertEquals("João", clienteRetornado.nome());
        assertEquals("Silva", clienteRetornado.sobrenome());
    }

    @Test
    void testObterClientePorId() {
        // Mock do cliente retornado pelo clienteRepository.findById
        Cliente cliente = new Cliente(1L, "João", "Silva", "123.456.789-00", "AB123456",
                LocalDate.of(1990, 5, 15), Generos.MASCULINO, null, "Brasil", "joao@example.com", "123456789");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Teste do método obterClientePorId
        Cliente clienteRetornado = clienteService.obterClientePorId(1L);

        // Verificações
        assertNotNull(clienteRetornado);
        assertEquals(1L, clienteRetornado.getId());
        assertEquals("João", clienteRetornado.getNome());
        assertEquals("Silva", clienteRetornado.getSobrenome());
    }

    @Test
    void testObterClientePorIdClienteNaoEncontradoException() {
        // Mock do clienteRepository.findById que retorna vazio
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Teste do método obterClientePorId
        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.obterClientePorId(1L));
    }

    @Test
    void testAtualizarCliente() {
        // Mock dos dados do cliente
        ClienteRecord clienteRecord = new ClienteRecord("João", "Silva", "123.456.789-00", "AB123456",
                LocalDate.of(1990, 5, 15), Generos.MASCULINO, null, "Brasil", "joao@example.com", "123456789");

        // Mock do cliente existente retornado pelo clienteRepository.findById
        Cliente clienteExistente = new Cliente(1L, "Maria", "Souza", "987.654.321-00", "CD987654",
                LocalDate.of(1992, 3, 20), Generos.FEMININO, null, "EUA", "maria@example.com", "987654321");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

        // Teste do método atualizarCliente
        Cliente clienteAtualizado = clienteService.atualizarCliente(1L, clienteRecord);

        // Verificações
        assertNotNull(clienteAtualizado);
        assertEquals(1L, clienteAtualizado.getId());
        assertEquals("João", clienteAtualizado.getNome());
        assertEquals("Silva", clienteAtualizado.getSobrenome());
        assertEquals("123.456.789-00", clienteAtualizado.getCpf());
        assertEquals("AB123456", clienteAtualizado.getPassaporte());
    }
    @Test
    void testExcluirCliente() {
        // Mock do cliente existente retornado pelo clienteRepository.findById
        Cliente clienteExistente = new Cliente(1L, "João", "Silva", "123.456.789-00", "AB123456",
                LocalDate.of(1990, 5, 15), Generos.MASCULINO, null, "Brasil", "joao@example.com", "123456789");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

        // Teste do método excluirCliente
        assertDoesNotThrow(() -> clienteService.excluirCliente(1L));

        // Verifica se o método clienteRepository.deleteById foi chamado
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testExcluirClienteClienteNaoEncontradoException() {
        // Mock do clienteRepository.findById que retorna vazio
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Teste do método excluirCliente
        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.excluirCliente(1L));

        // Verifica se o método clienteRepository.deleteById não foi chamado
        verify(clienteRepository, never()).deleteById(1L);
    }
}