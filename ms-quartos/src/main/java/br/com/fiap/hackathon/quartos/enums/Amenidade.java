package br.com.fiap.hackathon.quartos.enums;

public enum Amenidade {
  PISCINA_ADULTO_AQUECIDA_COBERTA("Piscina Adulto Aquecida Coberta"),
  PISCINA_ADULTO_NAO_AQUECIDA_ABERTA("Piscina Adulto Não Aquecida Aberta"),
  PISCINA_INFANTIL_AQUECIDA_COBERTA("Piscina Infantil Aquecida Coberta"),
  PISCINA_INFANTIL_NAO_AQUECIDA_ABERTA("Piscina Infantil Não Aquecida Aberta"),
  RESTAURANTE_SELF_SERVICE("Restaurante Self Service"),
  RESTAURANTE_A_LA_CARTE("Restaurante à La Carte"),
  AREA_KIDS("Área Kids"),
  EQUIPE_ENTRETENIMENTO_INFANTIL("Equipe de Entretenimento Infantil"),
  EQUIPE_ENTRETENIMENTO_ADULTO("Equipe de Entretenimento Adulto");

  private final String descricao;

  Amenidade(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
