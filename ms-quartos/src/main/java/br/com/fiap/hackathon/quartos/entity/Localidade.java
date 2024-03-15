package br.com.fiap.hackathon.quartos.entity;

import br.com.fiap.hackathon.quartos.enums.Amenidade;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "localidades")
public class Localidade {

  @Id private String id;
  private String nome;
  private Endereco endereco;

  private List<Amenidade> amenidades;

  @DBRef private List<Predio> predios;
}
