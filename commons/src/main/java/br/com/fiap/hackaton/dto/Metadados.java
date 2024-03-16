package br.com.fiap.hackaton.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Metadados implements Serializable {

    private String UUID;
    private TipoEventoEnum tipoEvento;

    public Metadados() {
    }

    public Metadados(String UUID, TipoEventoEnum tipoEvento) {
        this.UUID = UUID;
        this.tipoEvento = tipoEvento;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    @Override
    public String toString() {
        return "Metadados{" +
                "UUID='" + UUID + '\'' +
                ", tipoEvento=" + tipoEvento +
                '}';
    }

    public TipoEventoEnum getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEventoEnum tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
}