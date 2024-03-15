package br.com.fiap.hackaton.msservicos.controller;

import br.com.fiap.hackaton.msservicos.entity.ServicoOpcional;
import br.com.fiap.hackaton.msservicos.service.IServicoOpcionalService;
import br.com.fiap.hackaton.msservicos.service.impl.ServicoOpcionalServiceImpl;
import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalRequest;
import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos-opcionais")
@AllArgsConstructor
//@Tag(name = "Serviços e  Opcionais", description = "API de Serviços Opcionais")
public class ServicoOpcionalController {

    private IServicoOpcionalService service;

    

    @GetMapping
    @Operation(summary = "Listar serviços e opcionais", description = "LIsta paginada de serviços e opcionais")
    public ResponseEntity<Page<ServicoOpcionalResponse>> listarTodos(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ServicoOpcionalResponse> servicos = service.listarTodos(pageable);

        return ResponseEntity.ok(servicos);
    }

    @PostMapping
    @Operation(summary = "Cadastro de serviços e opcionais", description = "cadastro de serviços e opcionais")
    public ResponseEntity<ServicoOpcionalResponse> adicionarServicoOpcional(
            @RequestBody @Valid ServicoOpcionalRequest servicoOpcionalRequest) {

        return new ResponseEntity<>(service.adicionar(servicoOpcionalRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cadastro de serviços e opcionais", description = "Atualizar cadastro de serviços e opcionais")
    public ResponseEntity<ServicoOpcionalResponse> atualizarServicoOpcional(
            @PathVariable Long id,  @RequestBody @Valid ServicoOpcionalRequest servicoOpcionalRequest) throws Exception {

        return new ResponseEntity<>(service.atualizar(id,servicoOpcionalRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar de serviços e opcionais", description = "Deletar por id o cadastro de serviços e opcionais")
    public ResponseEntity<Void> removerServicoOpcional(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca de serviços e opcionais", description = "Busca de serviços e opcionais por ID")
    public ResponseEntity<ServicoOpcionalResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/nome")
    @Operation(summary = "Busca de serviços e opcionais", description = "Busca de serviços e opcionais por Nome")
    public ResponseEntity<List<ServicoOpcionalResponse>> buscarPorNome(@RequestParam String nome) {

        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

}
