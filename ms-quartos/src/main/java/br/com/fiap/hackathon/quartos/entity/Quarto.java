package br.com.fiap.hackathon.quartos.entity;

import br.com.fiap.hackathon.quartos.enums.Movel;
import br.com.fiap.hackathon.quartos.enums.TipoQuarto;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "quartos")
public class Quarto {
  @Id private String id;

  private TipoQuarto tipo;
  private int totalPessoas;
  private int totalCamas;
  private Double valorDiaria;
  private List<Movel> moveis;

  private String predioId;
}
