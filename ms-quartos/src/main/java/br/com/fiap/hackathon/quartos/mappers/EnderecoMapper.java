package br.com.fiap.hackathon.quartos.mappers;

import br.com.fiap.hackathon.quartos.dtos.EnderecoDto;
import br.com.fiap.hackathon.quartos.entity.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

  public EnderecoDto toDto(Endereco endereco) {
    EnderecoDto dto = new EnderecoDto();
    dto.setId(endereco.getId());
    dto.setRua(endereco.getRua());
    dto.setNumero(endereco.getNumero());
    dto.setCidade(endereco.getCidade());
    dto.setBairro(endereco.getBairro());
    dto.setEstado(endereco.getEstado());
    dto.setCep(endereco.getCep());
    return dto;
  }

  public Endereco toEntity(EnderecoDto dto) {
    Endereco endereco = new Endereco();
    endereco.setId(dto.getId());
    endereco.setRua(dto.getRua());
    endereco.setNumero(dto.getNumero());
    endereco.setCidade(dto.getCidade());
    endereco.setBairro(dto.getBairro());
    endereco.setEstado(dto.getEstado());
    endereco.setCep(dto.getCep());
    return endereco;
  }
}
