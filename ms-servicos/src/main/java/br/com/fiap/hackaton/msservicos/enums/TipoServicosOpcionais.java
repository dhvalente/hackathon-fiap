package br.com.fiap.hackaton.msservicos.enums;

public enum TipoServicosOpcionais {
    SERVICO(80d),
    ITEM(1d);

    private Double valorMinimo;

    TipoServicosOpcionais(Double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public Double getvalorMinimo() {
        return valorMinimo;
    }
}

    