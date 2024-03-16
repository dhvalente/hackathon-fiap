package br.com.fiap.hackaton.ms.reservas.client;

import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalRequest;
import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@FeignClient(name = "servicos-opcionais-service", url = "http://localhost:8085/servicos-opcionais")
public interface ServicoOpcionalClient {

    @GetMapping
    ResponseEntity<Page<ServicoOpcionalResponse>> listarTodos(
            @RequestParam("page") int page, @RequestParam("size") int size);

    @PostMapping
    ResponseEntity<ServicoOpcionalResponse> adicionarServicoOpcional(
            @RequestBody ServicoOpcionalRequest servicoOpcionalRequest);

    @PutMapping("/{id}")
    ResponseEntity<ServicoOpcionalResponse> atualizarServicoOpcional(
            @PathVariable Long id, @RequestBody ServicoOpcionalRequest servicoOpcionalRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> removerServicoOpcional(@PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<ServicoOpcionalResponse> buscarPorId(@PathVariable Long id);

    @GetMapping("/nome")
    ResponseEntity<List<ServicoOpcionalResponse>> buscarPorNome(@RequestParam String nome);
}