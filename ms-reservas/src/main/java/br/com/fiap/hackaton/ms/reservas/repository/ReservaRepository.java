package br.com.fiap.hackaton.ms.reservas.repository;


import br.com.fiap.hackaton.ms.reservas.domain.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;


public interface ReservaRepository extends MongoRepository<Reserva, String> {
  List<Reserva> findByQuartoIdsContainsAndDataSaidaGreaterThanEqualAndDataEntradaLessThanEqual(
      String quartoId, LocalDate dataEntrada, LocalDate dataSaida);
}
