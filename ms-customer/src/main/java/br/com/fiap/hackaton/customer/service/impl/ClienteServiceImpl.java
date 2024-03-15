package br.com.fiap.hackaton.customer.service.impl;

import br.com.fiap.hackaton.customer.entity.Cliente;
import br.com.fiap.hackaton.customer.exceptions.ClienteNaoEncontradoException;
import br.com.fiap.hackaton.customer.records.ClienteRecord;
import br.com.fiap.hackaton.customer.repository.ClienteRepository;
import br.com.fiap.hackaton.customer.repository.EnderecoRepository;
import br.com.fiap.hackaton.customer.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    private final EnderecoRepository enderecoRepository;

    @Override
    public Cliente criarCliente(ClienteRecord clienteRecord) {
        log.info("START - [Cliente] - criar");
        try {
            enderecoRepository.save(clienteRecord.endereco());
            Cliente cliente = Cliente.builder()
                    .nome(clienteRecord.nome())
                    .sobrenome(clienteRecord.sobrenome())
                    .cpf(clienteRecord.cpf())
                    .passaporte(clienteRecord.passaporte())
                    .dataDeAniversario(clienteRecord.dataDeAniversario())
                    .genero(clienteRecord.genero())
                    .endereco(clienteRecord.endereco())
                    .paisDeOrigem(clienteRecord.paisDeOrigem())
                    .email(clienteRecord.email())
                    .telefone(clienteRecord.telefone())
                    .build();
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            log.info("FINAL - [Cliente] - criar");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteRecord> obterTodosClientes(Pageable pageable) {
        log.info("START - [Cliente] - obterTodos");
        try {
            return new PageImpl<>(clienteRepository.findAll(pageable).stream()
                    .map(cliente -> new ClienteRecord(
                            cliente.getNome(),
                            cliente.getSobrenome(),
                            cliente.getCpf(),
                            cliente.getPassaporte(),
                            cliente.getDataDeAniversario(),
                            cliente.getGenero(),
                            cliente.getEndereco(),
                            cliente.getPaisDeOrigem(),
                            cliente.getEmail(),
                            cliente.getTelefone()))
                    .toList());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            log.info("FINAL - [Cliente] - obterTodos");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente obterClientePorId(Long id) {
        log.info("START - [Cliente] - obterClientePorId - PARAMETER[{}]", id);
        try {
            return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        } finally {
            log.info("FINAL - [Cliente] - obterClientePorId - PARAMETER[{}]", id);
        }
    }

    @Override
    public Cliente atualizarCliente(Long id, ClienteRecord clienteRecord) {
        try {
            Cliente existeCliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new ClienteNaoEncontradoException(id));

            BeanUtils.copyProperties(clienteRecord, existeCliente, "id");
            clienteRepository.save(existeCliente);
            return existeCliente;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            log.info("FINAL - [Cliente] - update - PARAMETER[{}}]", id);
        }
    }

    @Override
    @Transactional
    public void excluirCliente(Long id) {
        log.info("START - [Cliente] - delete - PARAMETER[{}}]", id);

        try {
            obterClientePorId(id);
            clienteRepository.deleteById(id);
        } catch (DatabaseException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            log.info("FINAL - [Cliente] - delete - PARAMETER[{}}]", id);
        }
    }
}