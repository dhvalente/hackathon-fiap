package br.com.fiap.hackathon.quartos.service;

import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.repository.QuartoRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuartoService {

  private final QuartoRepository quartoRepository;

  public QuartoService(QuartoRepository quartoRepository) {
    this.quartoRepository = quartoRepository;
    log.info("Serviço de Quarto iniciado.");
  }

  public List<Quarto> getAllQuartos() {
    log.info("Buscando todos os quartos.");
    return quartoRepository.findAll();
  }

  public Quarto getQuartoById(String id) {
    log.info("Buscando quarto pelo ID: {}", id);
    Optional<Quarto> quarto = quartoRepository.findById(id);
    if (quarto.isPresent()) {
      log.info("Quarto encontrado: {}", quarto.get());
      return quarto.get();
    } else {
      log.error("Quarto não encontrado para o ID: {}", id);
      throw new RuntimeException("Quarto não encontrado para o ID: " + id);
    }
  }

  public Quarto createQuarto(Quarto quarto) {
    log.info("Criando quarto: {}", quarto);
    return quartoRepository.save(quarto);
  }

  public Quarto updateQuarto(String id, Quarto novosDadosQuarto) {
    log.info("Atualizando quarto com ID: {}", id);
    Optional<Quarto> existingQuarto = quartoRepository.findById(id);
    if (existingQuarto.isPresent()) {
      Quarto updatedQuarto = existingQuarto.get();
      updatedQuarto.setTipo(novosDadosQuarto.getTipo());
      updatedQuarto.setTotalPessoas(novosDadosQuarto.getTotalPessoas());
      updatedQuarto.setTotalCamas(novosDadosQuarto.getTotalCamas());
      updatedQuarto.setValorDiaria(novosDadosQuarto.getValorDiaria());
      updatedQuarto.setMoveis(novosDadosQuarto.getMoveis());
      log.info("Quarto atualizado: {}", updatedQuarto);
      return quartoRepository.save(updatedQuarto);
    } else {
      log.error("Quarto não encontrado para atualização com o ID: {}", id);
      throw new RuntimeException("Quarto não encontrado para o ID: " + id);
    }
  }

  public void deleteQuarto(String id) {
    log.info("Deletando quarto com ID: {}", id);
    Optional<Quarto> existingQuarto = quartoRepository.findById(id);
    if (existingQuarto.isPresent()) {
      quartoRepository.delete(existingQuarto.get());
      log.info("Quarto deletado com sucesso.");
    } else {
      log.error("Quarto não encontrado para deleção com o ID: {}", id);
      throw new RuntimeException("Quarto não encontrado para o ID: " + id);
    }
  }

  public boolean existsById(String quartoId) {
    boolean exists = quartoRepository.existsById(quartoId);
    log.info("Verificando existência do quarto com ID: {}. Existe? {}", quartoId, exists);
    return exists;
  }
}
