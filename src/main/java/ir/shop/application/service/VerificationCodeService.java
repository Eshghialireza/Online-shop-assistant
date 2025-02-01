package ir.shop.application.service;

import ir.shop.application.entity.VerificationCode;
import ir.shop.application.service.base.BaseService;
import ir.shop.application.service.payload.request.SignUpRequest;

public interface VerificationCodeService extends BaseService<VerificationCode, Long> {
    VerificationCode sendByMail(String mail);

    VerificationCode sendByPhone(String phone);

    boolean validateCode(String code, SignUpRequest signUpRequest);
}
