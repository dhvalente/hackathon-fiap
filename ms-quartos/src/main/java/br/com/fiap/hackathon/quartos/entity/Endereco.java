package br.com.fiap.hackathon.quartos.entity;

import lombok.Data;

@Data
public class Endereco {
  private long id;

  private String rua;

  private String numero;

  private String cidade;

  private String bairro;

  private String estado;

  private String cep;
}
