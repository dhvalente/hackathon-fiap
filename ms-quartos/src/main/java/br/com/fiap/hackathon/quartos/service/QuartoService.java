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
  }

  public List<Quarto> getAllQuartos() {
    return quartoRepository.findAll();
  }

  public Quarto getQuartoById(String id) {
    Optional<Quarto> quarto = quartoRepository.findById(id);
    if (quarto.isPresent()) {
      return quarto.get();
    } else {
      throw new RuntimeException("Quarto not found for id: " + id);
    }
  }

  public Quarto createQuarto(Quarto quarto) {
    log.info("Creating quarto: {}", quarto);
    return quartoRepository.save(quarto);
  }

  public Quarto updateQuarto(String id, Quarto quarto) {
    Optional<Quarto> existingQuarto = quartoRepository.findById(id);
    if (existingQuarto.isPresent()) {
      Quarto updatedQuarto = existingQuarto.get();
      updatedQuarto.setTipo(quarto.getTipo());
      updatedQuarto.setTotalPessoas(quarto.getTotalPessoas());
      updatedQuarto.setTotalCamas(quarto.getTotalCamas());
      updatedQuarto.setValorDiaria(quarto.getValorDiaria());
      updatedQuarto.setMoveis(quarto.getMoveis());
      return quartoRepository.save(updatedQuarto);
    } else {
      throw new RuntimeException("Quarto not found for id: " + id);
    }
  }

  public void deleteQuarto(String id) {
    Optional<Quarto> existingQuarto = quartoRepository.findById(id);
    if (existingQuarto.isPresent()) {
      quartoRepository.delete(existingQuarto.get());
    } else {
      throw new RuntimeException("Quarto not found for id: " + id);
    }
  }
}
