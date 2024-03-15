package br.com.fiap.hackaton.msservicos.dto;

import br.com.fiap.hackaton.msservicos.entity.ServicoOpcional;
import br.com.fiap.hackaton.msservicos.enums.TipoServicosOpcionais;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicoOpcionalResponse {

    private Long id;
    private String nome;
    private double valor;
    @Enumerated(EnumType.STRING)
    private TipoServicosOpcionais tipo;



}
