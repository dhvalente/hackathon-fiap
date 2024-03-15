package br.com.fiap.hackathon.quartos.dtos;

import br.com.fiap.hackathon.quartos.enums.Movel;
import br.com.fiap.hackathon.quartos.enums.TipoQuarto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class QuartoDto {
  @Schema(description = "Identificador único do quarto", example = "1", required = true)
  private String id;

  @Schema(description = "Tipo do quarto", example = "STANDARD_SIMPLES", required = true)
  @NotNull(message = "O tipo do quarto não pode ser nulo")
  private TipoQuarto tipo;

  @Schema(
      description = "Número total de pessoas que o quarto comporta",
      example = "2",
      required = true)
  @Positive(message = "O número total de pessoas deve ser um número maior que zero")
  private int totalPessoas;

  @Schema(description = "Número total de camas no quarto", example = "2", required = true)
  @Positive(message = "O número total de camas deve ser um número maior que zero")
  private int totalCamas;

  @Schema(description = "Valor da diária do quarto", example = "150.00", required = true)
  @DecimalMin(
      value = "0.0",
      inclusive = false,
      message = "O valor da diária deve ser maior que zero")
  private BigDecimal valorDiaria;

  @Schema(description = "Lista de móveis disponíveis no quarto", required = true)
  @NotEmpty(message = "A lista de móveis não pode estar vazia")
  private List<Movel> moveis;

  @Schema(
      description = "Identificador do prédio ao qual o quarto pertence",
      example = "1",
      required = true)
  @NotNull(message = "O ID do prédio não pode ser nulo")
  private String predioId;
}
