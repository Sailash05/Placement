package com.placementserver.placementserver.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

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
                    "\n\nhttp://192.168.1.6:5500/PlacementApp/passwordreset/studentpasswordreset.html?token="+token+
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



    public void sendFacultyResetRequest(String name, long mobileno, String email, String token) {
        //System.out.println(email);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Reset Your Password");
            message.setText("Hello "+name+"\nYour mobile : "+mobileno+
                    "\n\nWe received a request to reset your password. If you made this request, please click the link below to reset your password:" +
                    "\n\nhttp://192.168.1.5:5500/PlacementApp/passwordreset/facultypasswordreset.html?token="+token+
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


    public void sendDefaulters(List<String> emails, String name, byte[] excelFile) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emails.toArray(new String[0]));
            helper.setSubject("Defaulters List - " + name);
            helper.setText("Dear faculty,\n\nPlease find attached the defaulters list for " + name + "."
                    +" The students listed have not submitted their responses within the given time frame."
                    +" Kindly take the necessary actions as required.\n\n"
                    + "For any queries, please feel free to contact us at placement.dto@gmail.com.\n\n"
                    + "Best regards,\nPlacement Training DTO");

            // Attach the Excel file
            helper.addAttachment(name + ".xlsx", new org.springframework.core.io.ByteArrayResource(excelFile));

            mailSender.send(message);
            //System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error while sending email: " + e.getMessage());
        }
    }
}
