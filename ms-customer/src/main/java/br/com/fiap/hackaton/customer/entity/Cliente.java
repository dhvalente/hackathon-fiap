package br.com.fiap.hackaton.customer.entity;

import br.com.fiap.hackaton.customer.enums.Generos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import java.time.LocalDate;


@Data
@Entity(name = "tb_users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    private String nome;

    private String sobrenome;

    private String cpf;

    private String passaporte;

    private LocalDate dataDeAniversario;

    private Generos genero;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Endereco endereco;

    private String paisDeOrigem;

    private String telefone;

    private String email;

}