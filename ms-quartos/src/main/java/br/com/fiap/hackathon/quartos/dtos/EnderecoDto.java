package br.com.fiap.hackathon.quartos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EnderecoDto {
  @Schema(description = "Identificador único do endereço", example = "1")
  private long id;

  @Schema(description = "Nome da rua", example = "Rua da Paz")
  @NotBlank(message = "A rua não pode estar em branco")
  private String rua;

  @Schema(description = "Número do local", example = "123")
  @NotBlank(message = "O número não pode estar em branco")
  private String numero;

  @Schema(description = "Nome da cidade", example = "São Paulo")
  @NotBlank(message = "A cidade não pode estar em branco")
  private String cidade;

  @Schema(description = "Nome do bairro", example = "Centro")
  @NotBlank(message = "O bairro não pode estar em branco")
  private String bairro;

  @Schema(description = "Nome do estado", example = "SP")
  @NotBlank(message = "O estado não pode estar em branco")
  private String estado;

  @Schema(description = "CEP do local", example = "12345-678", pattern = "\\d{5}-\\d{3}")
  @NotBlank(message = "O cep não pode estar em branco")
  @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido, o formato deve ser 00000-000")
  private String cep;
}
