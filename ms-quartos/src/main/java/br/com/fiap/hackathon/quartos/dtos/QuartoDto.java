package br.com.fiap.hackathon.quartos.dtos;

import br.com.fiap.hackathon.quartos.enums.Movel;
import br.com.fiap.hackathon.quartos.enums.TipoQuarto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class QuartoDto {
  private String id;

  @NotNull(message = "O tipo do quarto não ser nulo")
  private TipoQuarto tipo;

  @Positive(message = "O número total de pessoas deve ser um número maior que zero")
  private int totalPessoas;

  @Positive(message = "O número total de camas deve ser um número  maior que zero")
  private int totalCamas;

  @DecimalMin(
      value = "0.0",
      inclusive = false,
      message = "O valor da diária deve ser maior que zero")
  private double valorDiaria;

  @NotEmpty(message = "A lista de móveis não pode estar vazia")
  private List<Movel> moveis;
}
