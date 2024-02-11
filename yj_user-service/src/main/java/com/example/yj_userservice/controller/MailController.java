package com.example.yj_userservice.controller;


import com.example.yj_userservice.dto.request.EmailCertRequest;
import com.example.yj_userservice.dto.response.EmailCertResponse;
import com.example.yj_userservice.dto.response.Response;
import com.example.yj_userservice.service.MailSendService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mails")
public class MailController {

    private final MailSendService mailSendService;


    @PostMapping("/send-certification")
    public Response<EmailCertResponse> sendCertificationNumber(@RequestBody EmailCertRequest request)
            throws MessagingException, NoSuchAlgorithmException {
     ;
        return Response.success(mailSendService.sendEmailForCertification(request.getEmail()));
    }

    @GetMapping("/verify")
    public Response<Void> verifyCertificationNumber(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "certificationNumber") String certificationNumber
    ) {
        mailSendService.verifyEmail(email, certificationNumber);
        return Response.success();
    }
}