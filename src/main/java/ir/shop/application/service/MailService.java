package ir.shop.application.service;

import ir.shop.application.entity.VerificationCode;

public interface MailService {
    void sendVerificationCode(VerificationCode verificationCode);
}
