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
    log.info("Serviço de Prédio iniciado.");
  }

  public List<Predio> getAllPredios() {
    log.info("Buscando todos os prédios.");
    return predioRepository.findAll();
  }

  public Predio getPredioById(String id) {
    log.info("Buscando prédio pelo ID: {}", id);
    Optional<Predio> predio = predioRepository.findById(id);
    if (predio.isPresent()) {
      log.info("Prédio encontrado: {}", predio.get());
      return predio.get();
    } else {
      log.error("Prédio não encontrado para o ID: {}", id);
      throw new RuntimeException("Prédio não encontrado para o ID: " + id);
    }
  }

  public Predio createPredio(Predio predio) {
    log.info("Criando prédio: {}", predio);
    return predioRepository.save(predio);
  }

  public Predio updatePredio(String id, Predio novosDadosPredio) {
    log.info("Atualizando prédio com ID: {}", id);
    Optional<Predio> existingPredio = predioRepository.findById(id);
    if (existingPredio.isPresent()) {
      Predio updatedPredio = existingPredio.get();
      updatedPredio.setNome(novosDadosPredio.getNome());
      updatedPredio.setQuartos(novosDadosPredio.getQuartos());
      log.info("Prédio atualizado: {}", updatedPredio);
      return predioRepository.save(updatedPredio);
    } else {
      log.error("Prédio não encontrado para atualização com o ID: {}", id);
      throw new RuntimeException("Prédio não encontrado para o ID: " + id);
    }
  }

  public void deletePredio(String id) {
    log.info("Deletando prédio com ID: {}", id);
    Optional<Predio> existingPredio = predioRepository.findById(id);
    if (existingPredio.isPresent()) {
      predioRepository.delete(existingPredio.get());
      log.info("Prédio deletado com sucesso.");
    } else {
      log.error("Prédio não encontrado para deleção com o ID: {}", id);
      throw new RuntimeException("Prédio não encontrado para o ID: " + id);
    }
  }

  public boolean existsById(String predioId) {
    boolean exists = predioRepository.existsById(predioId);
    log.info("Verificando existência do prédio com ID: {}. Existe? {}", predioId, exists);
    return exists;
  }
}
