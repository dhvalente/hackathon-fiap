package br.com.fiap.hackathon.quartos.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

@Data
public class PredioDto {
  private String id;

  @NotBlank(message = "O nome do prédio não pode estar em branco")
  private String nome;

  @NotEmpty(message = "A lista de quartos não pode estar vazia")
  @Valid // Para validar os elementos da lista de quartos
  private List<QuartoDto> quartos;
}
