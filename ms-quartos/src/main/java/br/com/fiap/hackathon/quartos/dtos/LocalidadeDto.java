package br.com.fiap.hackathon.quartos.dtos;

import br.com.fiap.hackathon.quartos.enums.Amenidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class LocalidadeDto {
  private String id;

  @NotEmpty(message = "O nome da localidade não pode estar vazio")
  private String nome;

  @NotNull(message = "O endereço não pode estar vazio")
  @Valid
  private EnderecoDto endereco;

  @NotNull(message = "A lista de amenidades não pode ser nula")
  private List<Amenidade> amenidades;

  @NotNull(message = "A lista de prédios não pode ser nula")
  @Valid
  private List<PredioDto> predios;
}
