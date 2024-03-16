package br.com.fiap.hackathon.quartos.mappers;

import br.com.fiap.hackathon.quartos.dtos.LocalidadeDto;
import br.com.fiap.hackathon.quartos.entity.Localidade;

import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalidadeMapper {

  @Autowired private PredioMapper predioMapper;

  @Autowired private EnderecoMapper enderecoMapper;

  public LocalidadeDto toDto(Localidade localidade) {
    LocalidadeDto dto = new LocalidadeDto();
    dto.setId(localidade.getId());
    dto.setNome(localidade.getNome());
    dto.setEndereco(enderecoMapper.toDto(localidade.getEndereco()));
    dto.setAmenidades(localidade.getAmenidades());
    dto.setPredios(
        localidade.getPredios().stream()
                .filter(Objects::nonNull)
                .map(predioMapper::toDto)
                .collect(Collectors.toList()));
    return dto;
  }

  public Localidade toEntity(LocalidadeDto dto) {
    Localidade localidade = new Localidade();
    localidade.setId(dto.getId());
    localidade.setNome(dto.getNome());
    localidade.setEndereco(enderecoMapper.toEntity(dto.getEndereco()));
    localidade.setAmenidades(dto.getAmenidades());
    localidade.setPredios(
        dto.getPredios().stream().map(predioMapper::toEntity).collect(Collectors.toList()));
    return localidade;
  }
}
