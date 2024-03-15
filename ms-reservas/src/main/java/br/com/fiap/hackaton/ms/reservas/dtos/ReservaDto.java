package br.com.fiap.hackaton.ms.reservas.dtos;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class ReservaDto {
    private Long clienteId;
    private List<String> quartoIds;
    private List<String> servicoOpcionalIds;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private Integer numeroHospedes;

}

