package br.com.fiap.hackaton.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evento implements Serializable {

    private Metadados metadados;
    private Object payload;

}