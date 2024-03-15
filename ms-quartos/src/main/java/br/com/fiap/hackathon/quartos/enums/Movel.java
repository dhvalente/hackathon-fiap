package br.com.fiap.hackathon.quartos.enums;

public enum Movel {
  SOFA("Sofá"),
  POLTRONA("Poltrona"),
  FRIGOBAR("Frigobar"),
  TV_LED_54_POLS("TV LED 54 Polegadas"),
  MESA_DE_ESCRITORIO("Mesa de Escritório"),
  CADEIRA_DE_ESCRITORIO("Cadeira de Escritório"),
  TV_LED_62_POLS("TV LED 62 Polegadas");

  private final String descricao;

  Movel(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
