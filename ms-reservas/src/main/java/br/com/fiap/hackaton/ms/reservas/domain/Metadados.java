package br.com.fiap.hackaton.ms.reservas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadados implements Serializable {

    private String UUID;
    private String dataHora;
    private TipoEventoEnum tipoEvento;
}