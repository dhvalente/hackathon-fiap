package br.com.fiap.hackathon.quartos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

@Data
public class PredioDto {
  @Schema(description = "Identificador único do prédio", example = "1", required = true)
  private String id;

  @Schema(description = "Nome do prédio", example = "Prédio Principal", required = true)
  @NotBlank(message = "O nome do prédio não pode estar em branco")
  private String nome;

  @Schema(description = "Lista de quartos no prédio", required = true)
  @NotEmpty(message = "A lista de quartos não pode estar vazia")
  @Valid
  private List<QuartoDto> quartos;

  @Schema(
      description = "Identificador da localidade à qual o prédio pertence",
      example = "1",
      required = true)
  @NotBlank(message = "O id da localidade não pode estar em branco")
  private String localidadeId;
}
