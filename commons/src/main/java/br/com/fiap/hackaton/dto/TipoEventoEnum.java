package br.com.fiap.hackaton.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
public enum TipoEventoEnum implements Serializable {

    EXPIRACAO_RESERVA("expiracao_reserva", EventoExpiracaoReserva.class);
    private String nome;
    private Class classe;

    TipoEventoEnum(String nome, Class classe) {
        this.nome = nome;
        this.classe = classe;
    }

    public String getNome() {
        return nome;
    }

    public Class getClasse() {
        return classe;
    }

    public static Class obterClassePorNomeTipoEvento(String nomeTipoEvento){

        var tipoEventoEnum = Arrays.stream(TipoEventoEnum.values())
                .filter(eventoEnum -> eventoEnum.getNome().equals(nomeTipoEvento))
                .findFirst();

        if (tipoEventoEnum.isPresent())
            return tipoEventoEnum.get().classe;

        return null;
    }

}