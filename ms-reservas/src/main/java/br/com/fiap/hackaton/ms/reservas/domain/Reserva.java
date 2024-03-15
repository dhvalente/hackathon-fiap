package br.com.fiap.hackaton.ms.reservas.domain;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Reserva {

  @Id private String id;
  private String clienteId;
  private List<String> quartoIds;
  private List<String> servicoOpcionalIds;
  private LocalDate dataEntrada;
  private LocalDate dataSaida;
  private Integer numeroHospedes;
  private Double valorTotal;
}
