package br.com.fiap.hackaton.ms.reservas.service;


import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import br.com.fiap.hackaton.customer.entity.Cliente;
import br.com.fiap.hackaton.ms.reservas.client.ClienteClient;
import br.com.fiap.hackaton.ms.reservas.client.QuartoClient;
import br.com.fiap.hackaton.ms.reservas.client.ServicoOpcionalClient;
import br.com.fiap.hackaton.ms.reservas.domain.Reserva;
import br.com.fiap.hackaton.ms.reservas.dtos.ReservaDto;
import br.com.fiap.hackaton.ms.reservas.repository.ReservaRepository;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.hackaton.msservicos.dto.ServicoOpcionalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReservaService {

  @Autowired private ClienteClient clienteClient;
  @Autowired private ServicoOpcionalClient servicoOpcionalClient;
  @Autowired private QuartoClient quartoClient;
  @Autowired private ReservaRepository reservaRepository;

  public Reserva createReserva(ReservaDto reservaDTO) {

    log.info("Criando reserva para o cliente: {}", reservaDTO.getClienteId());
    ResponseEntity<Cliente> clienteResponse =
        clienteClient.findClienteById(reservaDTO.getClienteId());
    if (clienteResponse.getStatusCode().isError() || clienteResponse.getBody() == null) {
      log.error("Cliente não encontrado: {}", reservaDTO.getClienteId());
      throw new IllegalStateException("Cliente não encontrado.");
    }
    log.info("Cliente recuperado com sucesso: {}", clienteResponse.getBody());

    int quantidadeDeDias =
        reservaDTO.getDataSaida().getDayOfMonth() - reservaDTO.getDataEntrada().getDayOfMonth();
    log.info("Quantidade de dias: {}", quantidadeDeDias);

    double valorTotal = 0.0;
    List<QuartoDto> quartosSelecionados = new ArrayList<>();

    for (String quartoId : reservaDTO.getQuartoIds()) {
      ResponseEntity<QuartoDto> quartoResponse = quartoClient.getQuartoById(quartoId);
      if (quartoResponse.getStatusCode().isError() || quartoResponse.getBody() == null) {
        log.error("Quarto não encontrado: {}", quartoId);
        throw new IllegalStateException("Quarto não encontrado: " + quartoId);
      }

      List<Reserva> reservasExistentes =
          reservaRepository
              .findByQuartoIdsContainsAndDataSaidaGreaterThanEqualAndDataEntradaLessThanEqual(
                  quartoId, reservaDTO.getDataEntrada(), reservaDTO.getDataSaida());
      if (!reservasExistentes.isEmpty()) {
        log.error("Quarto já reservado para as datas solicitadas: {}", quartoId);
        throw new IllegalStateException(
            "Quarto já reservado para as datas solicitadas: " + quartoId);
      }

      log.info("Quarto disponível: {}", quartoResponse.getBody());
      quartosSelecionados.add(quartoResponse.getBody());
      valorTotal += (quartoResponse.getBody().getValorDiaria() * quantidadeDeDias);
    }

    log.info("Valor total após adicionar quartos: {}", valorTotal);

    for (String servicoId : reservaDTO.getServicoOpcionalIds()) {
      ResponseEntity<ServicoOpcionalResponse> servicoResponse =
          servicoOpcionalClient.buscarPorId(Long.parseLong(servicoId));
      if (servicoResponse.getStatusCode().isError() || servicoResponse.getBody() == null) {
        log.error("Serviço opcional não encontrado: {}", servicoId);
        throw new IllegalStateException("Serviço opcional não encontrado: " + servicoId);
      }
      log.info("Serviço opcional encontrado: {}", servicoResponse.getBody());
      valorTotal += (servicoResponse.getBody().getValor() * quantidadeDeDias);
    }
    log.info("Valor total final: {}", valorTotal);

    Reserva reserva = new Reserva();
    reserva.setClienteId(String.valueOf(reservaDTO.getClienteId()));
    reserva.setQuartoIds(reservaDTO.getQuartoIds());
    reserva.setServicoOpcionalIds(reservaDTO.getServicoOpcionalIds());
    reserva.setDataEntrada(reservaDTO.getDataEntrada());
    reserva.setDataSaida(reservaDTO.getDataSaida());
    reserva.setNumeroHospedes(reservaDTO.getNumeroHospedes());
    reserva.setValorTotal(valorTotal);

    Reserva savedReserva = reservaRepository.save(reserva);
    log.info("Reserva salva com sucesso: {}", savedReserva);
    // TODO : Implementar a lógica de envio de e-mail
    return savedReserva;
  }

  // TODO : Implementar os métodos de atualização e remoção de reservas

  public List<Reserva> findAllReservas() {
    log.info("Buscando todas as reservas");
    return reservaRepository.findAll();
  }

  public Reserva findReservaById(Long reservaId) {
    log.info("Buscando reserva com ID: {}", reservaId);
    return reservaRepository
        .findById(String.valueOf(reservaId))
        .orElseThrow(
            () -> new IllegalStateException("Reserva não encontrada com ID: " + reservaId));
  }

  public Reserva updateReserva(Long reservaId, ReservaDto reservaDTO) {
    log.info("Atualizando reserva com ID: {}", reservaId);
    Reserva reserva =
        reservaRepository
            .findById(String.valueOf(reservaId))
            .orElseThrow(
                () -> new IllegalStateException("Reserva não encontrada com ID: " + reservaId));

    // Atualize os campos necessários da reserva
    // Exemplo: reserva.setNumeroHospedes(reservaDTO.getNumeroHospedes());
    // Faça as verificações necessárias, como disponibilidade de quarto, etc.

    return reservaRepository.save(reserva);
  }

  public void deleteReserva(Long reservaId) {
    log.info("Removendo reserva com ID: {}", reservaId);
    boolean exists = reservaRepository.existsById(String.valueOf(reservaId));
    if (!exists) {
      throw new IllegalStateException("Reserva não encontrada com ID: " + reservaId);
    }
    reservaRepository.deleteById(String.valueOf(reservaId));
  }
}
