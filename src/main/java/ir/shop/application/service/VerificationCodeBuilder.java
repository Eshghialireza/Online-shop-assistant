package ir.shop.application.service;

import ir.shop.application.entity.Config;
import ir.shop.application.entity.VerificationCode;
import ir.shop.application.service.payload.request.SignUpRequest;

public class VerificationCodeBuilder {

    public VerificationCode sendCode(SignUpRequest signUpRequest,
                                     VerificationCodeService verificationCodeService,
                                     ConfigService configService) {
        VerificationCode verificationCode = new VerificationCode();
        Config config = configService.findByName("config");
        switch (config.getVerificationCodeContact()) {
            case MAIL -> verificationCode = verificationCodeService.sendByMail(signUpRequest.getMailAddress());
        }
        return verificationCode;
    }
}
