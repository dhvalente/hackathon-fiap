package br.com.fiap.hackaton.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TipoEventoEnum implements Serializable {

    EXPIRACAO_RESERVA("expiracao_reserva", EventoExpiracaoReserva.class);
    private String nome;
    private Class classe;

    public static Class obterClassePorNomeTipoEvento(String nomeTipoEvento){

        var tipoEventoEnum = Arrays.stream(TipoEventoEnum.values())
                .filter(eventoEnum -> eventoEnum.getNome().equals(nomeTipoEvento))
                .findFirst();

        if (tipoEventoEnum.isPresent())
            return tipoEventoEnum.get().classe;

        return null;
    }

}