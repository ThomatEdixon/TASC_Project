package org.jewelryshop.userservice.services.iplm;

import lombok.RequiredArgsConstructor;
import org.jewelryshop.userservice.services.interfaces.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    @Override
    public String sendEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        message.setFrom(email);
        mailSender.send(message);
        return "send email success";
    }
    public String generateOTP(){
        String otp = "";
        String characters = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        otp +=sb.toString();
        return otp;
    }
}
