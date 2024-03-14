package br.com.fiap.hackathon.quartos.mappers;

import br.com.fiap.hackathon.quartos.dtos.PredioDto;
import br.com.fiap.hackathon.quartos.entity.Predio;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PredioMapper {

  @Autowired private QuartoMapper quartoMapper;

  public PredioDto toDto(Predio predio) {
    PredioDto dto = new PredioDto();
    dto.setId(predio.getId());
    dto.setNome(predio.getNome());
    dto.setQuartos(
        predio.getQuartos().stream()
            .filter(Objects::nonNull)
            .map(quartoMapper::toDto)
            .collect(Collectors.toList()));
    return dto;
  }

  public Predio toEntity(PredioDto dto) {
    Predio predio = new Predio();
    predio.setId(dto.getId());
    predio.setNome(dto.getNome());
    predio.setQuartos(
        dto.getQuartos().stream().map(quartoMapper::toEntity).collect(Collectors.toList()));
    return predio;
  }
}
