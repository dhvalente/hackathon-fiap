package br.com.fiap.hackathon.quartos.repository;

import br.com.fiap.hackathon.quartos.entity.Predio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PredioRepository extends MongoRepository<Predio, String> {}
