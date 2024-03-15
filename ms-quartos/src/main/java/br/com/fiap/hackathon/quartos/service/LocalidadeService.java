package br.com.fiap.hackathon.quartos.service;

import br.com.fiap.hackathon.quartos.entity.Localidade;
import br.com.fiap.hackathon.quartos.repository.LocalidadeRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocalidadeService {

  private final LocalidadeRepository localidadeRepository;

  public LocalidadeService(LocalidadeRepository localidadeRepository) {
    this.localidadeRepository = localidadeRepository;
  }

  public List<Localidade> getAllLocalidades() {
    return localidadeRepository.findAll();
  }

  public Localidade getLocalidadeById(String id) {
    Optional<Localidade> localidade = localidadeRepository.findById(id);
    if (localidade.isPresent()) {
      return localidade.get();
    } else {
      throw new RuntimeException("Localidade not found for id: " + id);
    }
  }

  public Localidade createLocalidade(Localidade localidade) {
    log.info("Creating localidade: {}", localidade);
    return localidadeRepository.save(localidade);
  }

  public Localidade updateLocalidade(String id, Localidade localidade) {
    Optional<Localidade> existingLocalidade = localidadeRepository.findById(id);
    if (existingLocalidade.isPresent()) {
      Localidade updatedLocalidade = existingLocalidade.get();
      updatedLocalidade.setNome(localidade.getNome());
      updatedLocalidade.setEndereco(localidade.getEndereco());
      updatedLocalidade.setAmenidades(localidade.getAmenidades());
      updatedLocalidade.setPredios(localidade.getPredios());
      return localidadeRepository.save(updatedLocalidade);
    } else {
      throw new RuntimeException("Localidade not found for id: " + id);
    }
  }

  public void deleteLocalidade(String id) {
    Optional<Localidade> existingLocalidade = localidadeRepository.findById(id);
    if (existingLocalidade.isPresent()) {
      localidadeRepository.delete(existingLocalidade.get());
    } else {
      throw new RuntimeException("Localidade not found for id: " + id);
    }
  }
}
