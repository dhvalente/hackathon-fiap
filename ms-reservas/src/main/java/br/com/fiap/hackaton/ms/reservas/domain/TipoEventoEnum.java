package br.com.fiap.hackaton.ms.reservas.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@NoArgsConstructor
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