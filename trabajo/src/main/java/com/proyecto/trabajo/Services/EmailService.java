package com.proyecto.trabajo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    /**
     * Envía un correo de recuperación de contraseña
     * @param toEmail Correo del destinatario
     * @param token Token único de recuperación
     */
    public void sendPasswordResetEmail(String toEmail, String token) {
        String resetLink = frontendUrl + "/reset-password?token=" + token;
        
        String subject = "Recuperación de Contraseña - Tech Inventory";
        
        String body = "Hola,\n\n"
                + "Recibimos una solicitud para restablecer tu contraseña.\n\n"
                + "Haz clic en el siguiente enlace para crear una nueva contraseña:\n"
                + resetLink + "\n\n"
                + "Este enlace expirará en 30 minutos.\n\n"
                + "Si no solicitaste este cambio, ignora este correo.\n\n"
                + "Saludos,\n"
                + "Equipo de Tech Inventory";

        sendSimpleEmail(toEmail, subject, body);
    }

    /**
     * Método genérico para enviar correos
     */
    private void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        
        mailSender.send(message);
    }
}
