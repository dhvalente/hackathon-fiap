package br.com.fiap.hackathon.quartos.mappers;

import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import br.com.fiap.hackathon.quartos.entity.Quarto;
import org.springframework.stereotype.Component;

@Component
public class QuartoMapper {

    public QuartoDto toDto(Quarto quarto) {
        QuartoDto dto = new QuartoDto();
        dto.setId(quarto.getId());
        dto.setTipo(quarto.getTipo());
        dto.setTotalPessoas(quarto.getTotalPessoas());
        dto.setTotalCamas(quarto.getTotalCamas());
        dto.setValorDiaria(quarto.getValorDiaria());
        dto.setMoveis(quarto.getMoveis());
        return dto;
    }

    public Quarto toEntity(QuartoDto dto) {
        Quarto quarto = new Quarto();
        quarto.setId(dto.getId());
        quarto.setTipo(dto.getTipo());
        quarto.setTotalPessoas(dto.getTotalPessoas());
        quarto.setTotalCamas(dto.getTotalCamas());
        quarto.setValorDiaria(dto.getValorDiaria());
        quarto.setMoveis(dto.getMoveis());
        return quarto;
    }
}