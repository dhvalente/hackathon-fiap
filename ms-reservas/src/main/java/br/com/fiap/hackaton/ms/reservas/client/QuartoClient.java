package br.com.fiap.hackaton.ms.reservas.client;

import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "quartos-service", url = "http://localhost:8080/api/v1/quartos")
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
