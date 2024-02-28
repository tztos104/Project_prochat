package com.example.yj_userservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailCertResponse {
    private String email;
    private String certificationNumber;

    public static EmailCertResponse fromEmail(String email,String certificationNumber) {
        return new EmailCertResponse(
                email,
                certificationNumber
        );
    }
}
