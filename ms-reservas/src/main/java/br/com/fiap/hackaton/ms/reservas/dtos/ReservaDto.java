package br.com.fiap.hackaton.ms.reservas.dtos;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReservaDto {
    @NotNull(message = "O ID do cliente não pode ser nulo")
    private Long clienteId;

    @NotEmpty(message = "Pelo menos um quarto deve ser selecionado")
    private List<String> quartoIds;

    private List<String> servicoOpcionalIds;

    @NotNull(message = "A data de entrada não pode ser nula")
    private LocalDate dataEntrada;

    @NotNull(message = "A data de saída não pode ser nula")
    private LocalDate dataSaida;

    @Positive(message = "O número de hóspedes deve ser um número positivo")
    private Integer numeroHospedes;
}


