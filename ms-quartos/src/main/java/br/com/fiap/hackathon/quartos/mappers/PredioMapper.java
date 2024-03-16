package br.com.fiap.hackathon.quartos.mappers;

import br.com.fiap.hackathon.quartos.dtos.PredioDto;
import br.com.fiap.hackathon.quartos.entity.Predio;
import br.com.fiap.hackathon.quartos.entity.Quarto;
import br.com.fiap.hackathon.quartos.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PredioMapper {

  @Autowired private QuartoMapper quartoMapper;

  @Autowired private QuartoService quartoService;

  public PredioDto toDto(Predio predio) {
    PredioDto dto = new PredioDto();
    dto.setId(predio.getId());
    dto.setNome(predio.getNome());
    dto.setQuartos(
        predio.getQuartos().stream()
            .filter(Objects::nonNull)
            .map(quartoMapper::toDto)
            .collect(Collectors.toList()));
    // Mapeamento do localidadeId ao converter para DTO
    dto.setLocalidadeId(predio.getLocalidadeId());
    return dto;
  }

  public Predio toEntity(PredioDto dto) {
    Predio predio = new Predio();
    predio.setId(dto.getId());
    predio.setNome(dto.getNome());
    // Antes de atribuir quartos a predio, salve cada quarto individualmente
    List<Quarto> quartosSalvos =
        dto.getQuartos().stream()
            .map(quartoMapper::toEntity)
            .map(quartoService::createQuarto) // Supondo que createQuarto salva e retorna o quarto
            .collect(Collectors.toList());
    predio.setQuartos(quartosSalvos);
    predio.setLocalidadeId(dto.getLocalidadeId());
    return predio;
  }
}
