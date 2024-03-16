package br.com.fiap.hackaton.ms.reservas.client;

import br.com.fiap.hackaton.customer.entity.Cliente;
import br.com.fiap.hackaton.customer.records.ClienteRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cliente-service", url = "http://localhost:8080/v1/clientes")
public interface ClienteClient {


    @PostMapping
    ResponseEntity<Cliente> createCliente(@RequestBody ClienteRecord clienteRecord);

    @GetMapping
    ResponseEntity<Page<ClienteRecord>> findAllClientes(
            @RequestParam("page") Integer page, @RequestParam("size") Integer size);

    @GetMapping("/{id}")
    ResponseEntity<Cliente> findClienteById(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCliente(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    ResponseEntity<Cliente> updateCliente(
            @PathVariable("id") Long id, @RequestBody ClienteRecord clienteRecord);

}