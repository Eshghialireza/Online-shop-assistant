package ir.shop.application.service.impl;

import ir.shop.application.entity.Config;
import ir.shop.application.entity.VerificationCode;
import ir.shop.application.exception.InvalidVerificationCodeException;
import ir.shop.application.exception.CodeRequestTooSoonException;
import ir.shop.application.repository.VerificationCodeRepo;
import ir.shop.application.service.ConfigService;
import ir.shop.application.service.VerificationCodeService;
import ir.shop.application.service.base.BaseServiceImpl;
import ir.shop.application.service.payload.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class VerificationCodeServiceImpl extends BaseServiceImpl<VerificationCode, Long, VerificationCodeRepo> implements VerificationCodeService {
    public VerificationCodeServiceImpl(VerificationCodeRepo repo) {
        super(repo);
    }

    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    MailServiceImpl mailServiceImpl;
    @Autowired
    ConfigService configService;
    private final Random random = new Random();

    @Override
    public VerificationCode sendByMail(String mail) {
        // looking if there is a code for this mail
        checkCodeExist(mail);
        VerificationCode verificationCode = new VerificationCode(generateCode(), mail, LocalDateTime.now().plusMinutes(4));
        mailServiceImpl.sendVerificationCode(verificationCode);
        return repo.save(verificationCode);
    }

    @Override
    public VerificationCode sendByPhone(String phone) {
        return null;
    }

    @Override
    public boolean validateCode(String code, SignUpRequest signUpRequest) {
        Config config = configService.findByName("config");
        switch (config.getVerificationCodeContact()) {
            case MAIL -> {
                VerificationCode verificationCode = repo.findByContactDetail(signUpRequest.getMailAddress());
                return checkCodeStatus(code, verificationCode);
            }
            case PHONE -> {
                VerificationCode verificationCode = repo.findByContactDetail(signUpRequest.getPhoneNumber());
                return checkCodeStatus(code, verificationCode);
            }
        }
        return false;
    }

    private String generateCode() {
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private boolean checkCodeStatus(String code, VerificationCode verificationCode) {
        if (verificationCode.getCode().equals(code)) {
            return verificationCode.getExpiryDate().isAfter(LocalDateTime.now());
        }
        return false;
    }

    private void checkCodeExist(String contactDetail) {
        VerificationCode verificationCode = repo.findByContactDetail(contactDetail);
        if (verificationCode != null) {
            if (verificationCode.getExpiryDate().isAfter(LocalDateTime.now())) {
                long timeRemaining = ChronoUnit.SECONDS.between(LocalDateTime.now(), verificationCode.getExpiryDate());
                throw new CodeRequestTooSoonException("wait " + timeRemaining + " seconds");
            } else {
                repo.delete(verificationCode);
            }
        }
    }
}
