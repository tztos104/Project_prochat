package com.example.yj_userservice.controller;


import com.example.yj_userservice.model.request.EmailCertRequest;
import com.example.yj_userservice.model.response.EmailCertResponse;
import com.example.yj_userservice.model.response.Response;
import com.example.yj_userservice.service.MailSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
@Tag(name = "MailController", description = "메일 인증 관련 Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mails")
public class MailController {

    private final MailSendService mailSendService;

    @Operation(summary = "메일 보내기 API")
    @PostMapping("/send-certification")
    public Response<EmailCertResponse> sendCertificationNumber(@RequestBody EmailCertRequest request)
            throws MessagingException, NoSuchAlgorithmException {
     ;
        return Response.success(mailSendService.sendEmailForCertification(request.getEmail()));
    }
    @Operation(summary = "메일 검증 API")
    @GetMapping("/verify")
    public Response<Void> verifyCertificationNumber(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "certificationNumber") String certificationNumber
    ) {
        mailSendService.verifyEmail(email, certificationNumber);
        return Response.success();
    }
}