package br.com.fiap.hackaton.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metadados implements Serializable {

    private UUID uuid;
    private TipoEventoEnum tipoEvento;
    private LocalDateTime dataHora;

}