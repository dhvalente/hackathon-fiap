package br.com.fiap.hackathon.quartos.repository;

import br.com.fiap.hackathon.quartos.entity.Quarto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuartoRepository extends MongoRepository<Quarto, String> {}
