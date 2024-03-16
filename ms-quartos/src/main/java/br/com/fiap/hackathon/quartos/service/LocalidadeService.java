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
    log.info("Buscando todas as localidades.");
    return localidadeRepository.findAll();
  }

  public Localidade getLocalidadeById(String id) {
    log.info("Buscando localidade pelo ID: {}", id);
    Optional<Localidade> localidade = localidadeRepository.findById(id);
    if (localidade.isPresent()) {
      log.info("Localidade encontrada: {}", localidade.get());
      return localidade.get();
    } else {
      log.error("Localidade não encontrada para o ID: {}", id);
      throw new RuntimeException("Localidade não encontrada para o ID: " + id);
    }
  }

  public Localidade createLocalidade(Localidade localidade) {
    log.info("Criando localidade: {}", localidade);
    return localidadeRepository.save(localidade);
  }

  public Localidade updateLocalidade(String id, Localidade novaLocalidade) {
    log.info("Atualizando localidade com ID: {}", id);
    Optional<Localidade> existingLocalidade = localidadeRepository.findById(id);
    if (existingLocalidade.isPresent()) {
      Localidade updatedLocalidade = existingLocalidade.get();
      updatedLocalidade.setNome(novaLocalidade.getNome());
      updatedLocalidade.setEndereco(novaLocalidade.getEndereco());
      updatedLocalidade.setAmenidades(novaLocalidade.getAmenidades());
      updatedLocalidade.setPredios(novaLocalidade.getPredios());
      log.info("Localidade atualizada: {}", updatedLocalidade);
      return localidadeRepository.save(updatedLocalidade);
    } else {
      log.error("Localidade não encontrada para atualização com o ID: {}", id);
      throw new RuntimeException("Localidade não encontrada para o ID: " + id);
    }
  }

  public void deleteLocalidade(String id) {
    log.info("Deletando localidade com ID: {}", id);
    Optional<Localidade> existingLocalidade = localidadeRepository.findById(id);
    if (existingLocalidade.isPresent()) {
      localidadeRepository.delete(existingLocalidade.get());
      log.info("Localidade deletada com sucesso.");
    } else {
      log.error("Localidade não encontrada para deleção com o ID: {}", id);
      throw new RuntimeException("Localidade não encontrada para o ID: " + id);
    }
  }

  public boolean existsById(String localidadeId) {
    boolean exists = localidadeRepository.existsById(localidadeId);
    log.info("Verificando existência da localidade com ID: {}. Existe? {}", localidadeId, exists);
    return exists;
  }
}
