package br.com.fiap.hackaton.customer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_addresses")
public class Endereco {

    @Id
    @Column(name = "id_address")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String rua;

    private String numero;

    private String cidade;

    private String bairro;

    private String estado;

    private String cep;

}