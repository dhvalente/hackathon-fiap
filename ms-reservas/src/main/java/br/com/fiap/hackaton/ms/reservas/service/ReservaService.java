package br.com.fiap.hackaton.ms.reservas.service;

import br.com.fiap.hackathon.quartos.Exception.GenericException;
import br.com.fiap.hackathon.quartos.dtos.QuartoDto;
import br.com.fiap.hackaton.customer.entity.Cliente;

import br.com.fiap.hackaton.ms.reservas.domain.Evento;
import br.com.fiap.hackaton.ms.reservas.client.ClienteClient;
import br.com.fiap.hackaton.ms.reservas.client.QuartoClient;
import br.com.fiap.hackaton.ms.reservas.client.ServicoOpcionalClient;
import br.com.fiap.hackaton.ms.reservas.domain.*;
import br.com.fiap.hackaton.ms.reservas.dtos.ReservaDto;
import br.com.fiap.hackaton.ms.reservas.repository.ReservaRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
  @Autowired private EventoService eventoService;

  public Reserva createReserva(ReservaDto reservaDTO) {
    log.info("Criando reserva para o cliente: {}", reservaDTO.getClienteId());
    ResponseEntity<Cliente> clienteResponse =
            clienteClient.findClienteById(reservaDTO.getClienteId());
    if (clienteResponse.getStatusCode().isError() || clienteResponse.getBody() == null) {
      log.error("Cliente não encontrado: {}", reservaDTO.getClienteId());
      throw new GenericException("Cliente não encontrado.");
    }
    log.info("Cliente recuperado com sucesso: {}", clienteResponse.getBody());

    long quantidadeDeDias =
            ChronoUnit.DAYS.between(reservaDTO.getDataEntrada(), reservaDTO.getDataSaida()) + 1;
    log.info("Quantidade de dias: {}", quantidadeDeDias);

    double valorTotal = 0.0;
    List<QuartoDto> quartosSelecionados = new ArrayList<>();

    for (String quartoId : reservaDTO.getQuartoIds()) {
      ResponseEntity<QuartoDto> quartoResponse = quartoClient.getQuartoById(quartoId);
      if (quartoResponse.getStatusCode().isError() || quartoResponse.getBody() == null) {
        log.error("Quarto não encontrado: {}", quartoId);
        throw new GenericException("Quarto não encontrado: " + quartoId);
      }

      List<Reserva> reservasExistentes =
              reservaRepository
                      .findByQuartoIdsContainsAndDataSaidaGreaterThanEqualAndDataEntradaLessThanEqual(
                              quartoId, reservaDTO.getDataEntrada(), reservaDTO.getDataSaida());
      if (!reservasExistentes.isEmpty()) {
        log.error("Quarto já reservado para as datas solicitadas: {}", quartoId);
        throw new GenericException("Quarto já reservado para as datas solicitadas: " + quartoId);
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
        throw new GenericException("Serviço opcional não encontrado: " + servicoId);
      }
      log.info("Serviço opcional encontrado: {}", servicoResponse.getBody());
      valorTotal +=
              servicoResponse
                      .getBody()
                      .getValor(); // Os serviços opcionais são cobrados por reserva, não por dia
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
    notificar(reservaDTO);

    return savedReserva;
  }

  public List<Reserva> findAllReservas() {
    log.info("Buscando todas as reservas");
    return reservaRepository.findAll();
  }

  public Reserva findReservaById(String reservaId) {
    log.info("Buscando reserva com ID: {}", reservaId);
    return reservaRepository
            .findById(reservaId)
            .orElseThrow(() -> new GenericException("Reserva não encontrada com ID: " + reservaId));
  }

  public Reserva updateReserva(String reservaId, ReservaDto reservaDTO) {
    log.info("Atualizando reserva com ID: {}", reservaId);
    Reserva reserva =
            reservaRepository
                    .findById(String.valueOf(reservaId))
                    .orElseThrow(() -> new GenericException("Reserva não encontrada com ID: " + reservaId));

    // Verifica se o cliente existe
    ResponseEntity<Cliente> clienteResponse =
            clienteClient.findClienteById(reservaDTO.getClienteId());
    if (clienteResponse.getStatusCode().isError() || clienteResponse.getBody() == null) {
      throw new GenericException("Cliente não encontrado.");
    }

    long quantidadeDeDias =
            ChronoUnit.DAYS.between(reservaDTO.getDataEntrada(), reservaDTO.getDataSaida()) + 1;

    double valorTotal = 0.0;
    List<String> quartosSelecionados = new ArrayList<>();

    // Verifica a disponibilidade de cada quarto
    for (String quartoId : reservaDTO.getQuartoIds()) {
      ResponseEntity<QuartoDto> quartoResponse = quartoClient.getQuartoById(quartoId);
      if (quartoResponse.getStatusCode().isError() || quartoResponse.getBody() == null) {
        throw new GenericException("Quarto não encontrado: " + quartoId);
      }

      // Aqui você deve implementar uma lógica para verificar se o quarto está disponível
      // para as novas datas, se necessário.

      quartosSelecionados.add(quartoId);
      valorTotal += quartoResponse.getBody().getValorDiaria() * quantidadeDeDias;
    }

    // Verifica cada serviço opcional
    for (String servicoId : reservaDTO.getServicoOpcionalIds()) {
      ResponseEntity<ServicoOpcionalResponse> servicoResponse =
              servicoOpcionalClient.buscarPorId(Long.parseLong(servicoId));
      if (servicoResponse.getStatusCode().isError() || servicoResponse.getBody() == null) {
        throw new GenericException("Serviço opcional não encontrado: " + servicoId);
      }

      valorTotal += servicoResponse.getBody().getValor();
    }

    // Atualiza os dados da reserva
    reserva.setClienteId(reservaDTO.getClienteId().toString());
    reserva.setQuartoIds(reservaDTO.getQuartoIds());
    reserva.setServicoOpcionalIds(reservaDTO.getServicoOpcionalIds());
    reserva.setDataEntrada(reservaDTO.getDataEntrada());
    reserva.setDataSaida(reservaDTO.getDataSaida());
    reserva.setNumeroHospedes(reservaDTO.getNumeroHospedes());
    reserva.setValorTotal(valorTotal);

    return reservaRepository.save(reserva);
  }

  public void deleteReserva(String reservaId) {
    log.info("Removendo reserva com ID: {}", reservaId);
    boolean exists = reservaRepository.existsById(String.valueOf(reservaId));
    if (!exists) {
      throw new GenericException("Reserva não encontrada com ID: " + reservaId);
    }
    reservaRepository.deleteById(String.valueOf(reservaId));
  }

  public void notificar(ReservaDto reservaDTO) {
    log.info("Notificando cliente da reserva: {}", reservaDTO);
    Cliente cliente =
            Objects.requireNonNull(clienteClient.findClienteById(reservaDTO.getClienteId()).getBody());

    Metadados metadados = new Metadados();
    metadados.setUUID(UUID.randomUUID().toString());
    metadados.setDataHora(LocalDateTime.now().toString());
    metadados.setTipoEvento(TipoEventoEnum.EXPIRACAO_RESERVA);

    EventoExpiracaoReserva eventoExpiracaoReserva = new EventoExpiracaoReserva();
    eventoExpiracaoReserva.setDestinatario(cliente.getEmail());
    eventoExpiracaoReserva.setAssunto("Reserva Expirada");
    eventoExpiracaoReserva.setMensagem("Olá usuario, sua reserva Expirada");

    Evento evento = new Evento();
    evento.setMetadados(metadados);
    evento.setPayload(eventoExpiracaoReserva);

    eventoService.publicarEvento(evento);

  }
}
