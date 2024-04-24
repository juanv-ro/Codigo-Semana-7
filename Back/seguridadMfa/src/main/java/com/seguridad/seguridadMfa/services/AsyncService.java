package com.seguridad.seguridadMfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.seguridad.seguridadMfa.dto.GenerarCodigoMfa;

@Service
public class AsyncService {

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean enviarCorreo(GenerarCodigoMfa request, String codigoMfa) {
        try {
            // Crear un objeto SimpleMailMessage
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Configurar el correo electrónico
            mailMessage.setTo(request.getCorreo());
            mailMessage.setSubject(request.getAsunto());
            mailMessage.setText("Su código MFA es: " + codigoMfa);

            // Enviar el correo electrónico
            javaMailSender.send(mailMessage);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
