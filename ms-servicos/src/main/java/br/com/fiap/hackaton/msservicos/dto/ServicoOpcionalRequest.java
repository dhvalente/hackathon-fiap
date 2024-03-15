package br.com.fiap.hackaton.msservicos.dto;

import br.com.fiap.hackaton.msservicos.enums.TipoServicosOpcionais;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicoOpcionalRequest {

    @NotBlank(message = "Nome é Obrigatório")
    private String nome;
    @PositiveOrZero(message = "Valor deve ser maior que zero")
    private double valor;
    @Enumerated(EnumType.STRING)
    private TipoServicosOpcionais tipo;

}
