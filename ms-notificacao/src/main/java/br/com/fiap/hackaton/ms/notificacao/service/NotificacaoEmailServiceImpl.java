package br.com.fiap.hackaton.ms.notificacao.service;

import br.com.fiap.hackaton.ms.notificacao.configuration.NotificacaoProperties;
import br.com.fiap.hackaton.ms.notificacao.domain.EventoExpiracaoReserva;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacaoEmailServiceImpl implements NotificacaoService{

    private final JavaMailSender javaMailSender;
    private final NotificacaoProperties notificacaoProperties;

    @Override
    public Boolean notificar(EventoExpiracaoReserva evento) {

        try {

            log.info("[EMAIL] - [INICIO] - Processamento Evento Notificacao Email.");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(notificacaoProperties.getFrom());
            message.setTo(evento.getDestinatario());
            message.setSubject(evento.getAssunto());
            message.setText(evento.getMensagem());
            javaMailSender.send(message);

            log.info("[EMAIL] - [SUCESSO] - Processamento Evento Notificacao Email.");
            return true;

        }catch (MailException exception) {
            log.error("[EMAIL] - [ERRO] - Processamento Evento Notificacao Email {}.", exception.getStackTrace());
        }finally {
            log.info("[EMAIL] - [FIM] - Processamento Evento Notificacao Email.");
        }

        return false;
    }

}