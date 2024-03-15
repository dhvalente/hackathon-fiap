package br.com.fiap.hackaton.msservicos.entity;

import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalRequest;
import br.com.fiap.hackaton.msservicos.enums.TipoServicosOpcionais;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
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






}