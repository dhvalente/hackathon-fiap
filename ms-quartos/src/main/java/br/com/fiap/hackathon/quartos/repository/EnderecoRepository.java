package br.com.fiap.hackathon.quartos.repository;

import br.com.fiap.hackathon.quartos.entity.Endereco;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnderecoRepository extends MongoRepository<Endereco, String> {}
