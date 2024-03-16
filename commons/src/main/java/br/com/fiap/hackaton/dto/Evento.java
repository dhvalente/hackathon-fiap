package br.com.fiap.hackaton.dto;

import java.io.Serializable;

public class Evento implements Serializable {

    private Metadados metadados;
    private Object payload;

    public Evento() {
    }

    public Evento(Metadados metadados, Object payload) {
        this.metadados = metadados;
        this.payload = payload;
    }

    public Metadados getMetadados() {
        return metadados;
    }

    public void setMetadados(Metadados metadados) {
        this.metadados = metadados;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "metadados=" + metadados +
                ", payload=" + payload +
                '}';
    }
}