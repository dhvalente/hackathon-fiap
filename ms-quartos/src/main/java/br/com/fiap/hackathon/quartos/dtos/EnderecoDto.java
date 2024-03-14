package br.com.fiap.hackathon.quartos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EnderecoDto {
  private long id;

  @NotBlank(message = "A rua não pode estar em branco")
  private String rua;

  @NotBlank(message = "O número não pode estar em branco")
  private String numero;

  @NotBlank(message = "A cidade não pode estar em branco")
  private String cidade;

  @NotBlank(message = "O bairro não pode estar em branco")
  private String bairro;

  @NotBlank(message = "O estado não pode estar em branco")
  private String estado;

  @NotBlank(message = "O cep não pode estar em branco")
  @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido, o formato deve ser 00000-000")
  private String cep;
}
