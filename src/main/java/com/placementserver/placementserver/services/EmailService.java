package com.placementserver.placementserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetRequest(String name, long rollno, String email, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Reset Your Password");
            message.setText("Hello "+name+"\nYour rollno : "+rollno+
                    "\n\nWe received a request to reset your password. If you made this request, please click the link below to reset your password:" +
                    "\n\nhttp://192.168.1.8:5500/PlacementApp/index.html?token="+token+
                    "\n\nIf you did not request a password reset, you can ignore this email. Your account is still secure." +
                    "\n\nFor security reasons, this link will expire in 10 minutes." +
                    "\n\nIf you encounter any issues, feel free to contact us at placement.dto@gmail.com." +
                    "\n\nBest regards,\n" +
                    "Placement Training DTO");
            message.setFrom("sailashs2005@gmail.com");
            mailSender.send(message);
        } catch (Exception e) {
            // Log the exception and rethrow it if needed
            System.out.println("Error sending email: " + e.getMessage());
            throw new RuntimeException("Error sending email. Please try again later.");
        }

    }
}
