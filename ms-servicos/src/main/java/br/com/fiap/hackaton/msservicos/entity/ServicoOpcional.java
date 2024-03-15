package br.com.fiap.hackaton.msservicos.entity;

import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalRequest;
import br.com.fiap.hackaton.msservicos.enums.TipoServicosOpcionais;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name="TB_SERVICO_OPCIONAL")
public class ServicoOpcional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double valor;
    @Enumerated(EnumType.STRING)
    private TipoServicosOpcionais tipo;


    public ServicoOpcional() {

    }

    public ServicoOpcional(Long id, String nome, double valor, TipoServicosOpcionais tipo) {
        this.setId(id);
        this.setNome(nome);
        this.setTipo(tipo);
        this.setValor(valor);

    }

    public ServicoOpcional(ServicoOpcionalRequest servicoOpcionalRequest) {

        this.setNome(servicoOpcionalRequest.getNome());
        this.setTipo(servicoOpcionalRequest.getTipo());
        this.setValor(servicoOpcionalRequest.getValor());

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(TipoServicosOpcionais tipo) {
        this.tipo = tipo;
    }

    public void setValor(double valor) {

        if(Double.compare(valor, this.getTipo().getvalorMinimo()) < 0 )
            throw new IllegalArgumentException("Valor menor que o padronizado");

        this.valor = valor;

    }


}