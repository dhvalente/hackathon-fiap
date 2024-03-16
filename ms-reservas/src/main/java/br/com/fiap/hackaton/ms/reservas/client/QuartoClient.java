package br.com.fiap.hackaton.ms.reservas.client;

import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@FeignClient(name = "quartos-service", url = "http://localhost:8082/api/v1/quartos")
public interface QuartoClient {

    @GetMapping
    ResponseEntity<List<QuartoDto>> getAllQuartos();

    @GetMapping("/{id}")
    ResponseEntity<QuartoDto> getQuartoById(@PathVariable String id);

    @PostMapping
    ResponseEntity<QuartoDto> createQuarto(@RequestBody QuartoDto quartoDto);

    @PutMapping("/{id}")
    ResponseEntity<QuartoDto> updateQuarto(@PathVariable String id, @RequestBody QuartoDto quartoDto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteQuarto(@PathVariable String id);
}