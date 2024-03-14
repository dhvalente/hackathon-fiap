package br.com.fiap.hackathon.quartos.entity;


import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "predios")
public class Predio {

    @Id
    private String id;
    private String nome;

    @DBRef
    private List<Quarto> quartos;
}
