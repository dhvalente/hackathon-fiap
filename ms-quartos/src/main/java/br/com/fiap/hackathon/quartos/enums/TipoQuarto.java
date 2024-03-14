package br.com.fiap.hackathon.quartos.enums;

public enum TipoQuarto {
  STANDARD_SIMPLES("Standard Simples"),
  STANDARD_DUPLO("Standard Duplo"),
  LUXO_SIMPLES("Luxo Simples"),
  LUXO_DUPLO("Luxo Duplo"),
  PREMIUM_SIMPLES("Premium Simples"),
  PREMIUM_DUPLO("Premium Duplo");

  private final String descricao;

  TipoQuarto(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
