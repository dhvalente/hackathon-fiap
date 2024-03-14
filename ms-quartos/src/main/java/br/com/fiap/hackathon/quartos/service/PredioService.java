package br.com.fiap.hackathon.quartos.service;

import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.repository.PredioRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PredioService {

  private final PredioRepository predioRepository;

  public PredioService(PredioRepository predioRepository) {
    this.predioRepository = predioRepository;
  }

  public List<Predio> getAllPredios() {
    return predioRepository.findAll();
  }

  public Predio getPredioById(String id) {
    Optional<Predio> predio = predioRepository.findById(id);
    if (predio.isPresent()) {
      return predio.get();
    } else {
      throw new RuntimeException("Predio not found for id: " + id);
    }
  }

  public Predio createPredio(Predio predio) {
    log.info("Creating predio: {}", predio);
    return predioRepository.save(predio);
  }

  public Predio updatePredio(String id, Predio predio) {
    Optional<Predio> existingPredio = predioRepository.findById(id);
    if (existingPredio.isPresent()) {
      Predio updatedPredio = existingPredio.get();
      updatedPredio.setNome(predio.getNome());
      updatedPredio.setQuartos(predio.getQuartos());
      return predioRepository.save(updatedPredio);
    } else {
      throw new RuntimeException("Predio not found for id: " + id);
    }
  }

  public void deletePredio(String id) {
    Optional<Predio> existingPredio = predioRepository.findById(id);
    if (existingPredio.isPresent()) {
      predioRepository.delete(existingPredio.get());
    } else {
      throw new RuntimeException("Predio not found for id: " + id);
    }
  }
}
