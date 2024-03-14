package br.com.fiap.hackathon.quartos.dtos;

import br.com.fiap.hackathon.quartos.enums.Amenidade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class LocalidadeDto {
  @Schema(description = "Identificador único da localidade", example = "1", required = true)
  private String id;

  @Schema(description = "Nome da localidade", example = "Localidade Central", required = true)
  @NotEmpty(message = "O nome da localidade não pode estar vazio")
  private String nome;

  @Schema(description = "Endereço da localidade", required = true)
  @NotNull(message = "O endereço não pode estar vazio")
  @Valid
  private EnderecoDto endereco;

  @Schema(description = "Lista de amenidades disponíveis na localidade", required = true)
  @NotNull(message = "A lista de amenidades não pode ser nula")
  private List<Amenidade> amenidades;

  @Schema(description = "Lista de prédios pertencentes à localidade", required = true)
  @NotNull(message = "A lista de prédios não pode ser nula")
  @Valid
  private List<PredioDto> predios;
}
