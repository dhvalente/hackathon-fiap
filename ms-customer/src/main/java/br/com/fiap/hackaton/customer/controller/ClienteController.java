package br.com.fiap.hackaton.customer.controller;

import br.com.fiap.hackaton.customer.entity.Cliente;
import br.com.fiap.hackaton.customer.records.ClienteRecord;
import br.com.fiap.hackaton.customer.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cliente Controller", description = "O Controlador de Clientes expõe APIs REST para clientes")
@RestController
@RequestMapping("/v1/clientes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Salvar API REST de cliente", description = "Salvar objeto cliente em um banco de dados")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> createPost(@RequestBody @Valid ClienteRecord clienteRecord ){
        Cliente user = clienteService.criarCliente(clienteRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @GetMapping
    @Operation(summary = "Obter API REST de clientes", description = "Obter todos os clientes do banco de dados")
    public ResponseEntity<Page<ClienteRecord>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ClienteRecord>  list = clienteService.obterTodosClientes(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value="/{id}")
    @Operation(summary  = "Obter API REST de cliente por ID", description = "Obter cliente por ID do banco de dados")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        Cliente cliente =  clienteService.obterClientePorId(id);
        return ResponseEntity.ok().body(cliente);
    }


    @DeleteMapping(value="/{id}")
    @Operation(summary = "Deletar API REST de cliente", description = "Deletar objeto de cliente por ID em um banco de dados")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Atualizar API REST de cliente", description = "Atualizar objeto de cliente por ID em um banco de dados")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id , @RequestBody ClienteRecord userRecord) {
        return ResponseEntity.ok(clienteService.atualizarCliente(id, userRecord));
    }

}