package br.com.fiap.hackaton.customer.service;

import br.com.fiap.hackaton.customer.entity.Cliente;
import br.com.fiap.hackaton.customer.records.ClienteRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {

    Cliente criarCliente(ClienteRecord clienteRecord);
    Page<ClienteRecord> obterTodosClientes(Pageable pageable);
    Cliente obterClientePorId(Long id);
    Cliente atualizarCliente(Long id, ClienteRecord clienteRecord);
    void excluirCliente(Long id);
}
