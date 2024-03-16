package br.com.fiap.hackaton.ms.reservas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoExpiracaoReserva implements Serializable {

    private String mensagem;
}