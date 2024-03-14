package br.com.fiap.hackathon.quartos.repository;

import br.com.fiap.hackathon.quartos.entity.Localidade;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocalidadeRepository extends MongoRepository<Localidade, String> {}
