package br.com.fiap.hackaton.msservicos.dto;

import br.com.fiap.hackaton.msservicos.entity.ServicoOpcional;
import br.com.fiap.hackaton.msservicos.enums.TipoServicosOpcionais;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServicoOpcionalResponse {

    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("valor")
    private double valor;

    @JsonProperty("tipo")
    @Enumerated(EnumType.STRING)
    private TipoServicosOpcionais tipo;


    public ServicoOpcionalResponse(ServicoOpcional servicoOpcional) {
        this.id = servicoOpcional.getId();
        this.nome = servicoOpcional.getNome();
        this.tipo = servicoOpcional.getTipo();
        this.valor = servicoOpcional.getValor();
    }


}
