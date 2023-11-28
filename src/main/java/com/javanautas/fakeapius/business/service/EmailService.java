package com.javanautas.fakeapius.business.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${send.mail.from}")
    private String mailFrom;

    @Value("${send.mail.to}")
    private String mailTo;

    private final JavaMailSender javaMailSender;

    public void enviaEmailExcecao(Exception e) {
        try{
            final MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(mailFrom));
            mimeMessageHelper.setTo(InternetAddress.parse(mailTo));
            mimeMessageHelper.setSubject("Notificação de erro no sistema");
            mimeMessageHelper.setText("Ocorreu um erro no sistema" + "\n" + e.getMessage() + "\n" + LocalDateTime.now());

            javaMailSender.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
