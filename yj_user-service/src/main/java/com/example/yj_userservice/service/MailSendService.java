package com.example.yj_userservice.service;


import com.example.yj_userservice.config.EncoderConfig;
import com.example.yj_userservice.model.response.EmailCertResponse;
import com.example.yj_userservice.exception.ErrorCode;
import com.example.yj_userservice.exception.ProchatException;
import com.example.yj_userservice.repository.CertificationNumber;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;



@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender mailSender;
    private final CertificationNumber certificationNumberDao;
    private final EncoderConfig generator;

    public EmailCertResponse sendEmailForCertification(String email) throws NoSuchAlgorithmException, MessagingException {

        String certificationNumber = generator.createCertificationNumber();
        String title = "Prochat 이메일 인증";
        String content = "<html><a href= http://localhost:8080/api/v1/users/verify?certificationNumber="+certificationNumber+"&email="+email+">" +"링크를 3분 이내에 클릭해주세요.</a></html>" ;
        certificationNumberDao.saveCertificationNumber(email, certificationNumber);
        sendMail(email, title, content);
        return new EmailCertResponse(email, certificationNumber);
    }

    private void sendMail(String email,String title, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject(title);
        helper.setText(content, true);
        mailSender.send(mimeMessage);
    }

    public void verifyEmail(String email, String certificationNumber) {
        if (!isVerify(email, certificationNumber)) {
            throw new ProchatException(ErrorCode.INVALID_EMAIL_OR_CERTIFICATION_NUMBER);
        }
        certificationNumberDao.removeCertificationNumber(email);
    }

    private boolean isVerify(String email, String certificationNumber) {
        boolean validatedEmail = isEmailExists(email);
        if (!isEmailExists(email)) {
            throw new ProchatException(ErrorCode.EMAIL_NOT_FOUND);
        }
        return (validatedEmail &&
                certificationNumberDao.getCertificationNumber(email).equals(certificationNumber));
    }

    private boolean isEmailExists(String email) {
        return certificationNumberDao.hasKey(email);
    }
}