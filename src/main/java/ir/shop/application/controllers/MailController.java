package ir.shop.application.controllers;

import ir.shop.application.entity.VerificationCode;
import ir.shop.application.repository.UserRepo;
import ir.shop.application.service.ConfigService;
import ir.shop.application.service.VerificationCodeBuilder;
import ir.shop.application.service.VerificationCodeService;
import ir.shop.application.service.impl.MailServiceImpl;
import ir.shop.application.service.payload.request.SignUpRequest;
import ir.shop.application.service.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    UserRepo userRepository;
    @Autowired
    VerificationCodeService verificationCodeService;
    @Autowired
    ConfigService configService;

    @PostMapping("/verificate")
    public ResponseEntity<?> sendVerificationCode(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByMailAddress(signUpRequest.getMailAddress())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Mail Address is already in use!"));
        }
        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: PhoneNumber is already in use!"));
        }
        VerificationCodeBuilder verificationCodeBuilder = new VerificationCodeBuilder();
        VerificationCode verificationCode = verificationCodeBuilder.sendCode(signUpRequest, verificationCodeService, configService);
        return ResponseEntity.ok(new MessageResponse("code successfully send to :" + verificationCode.getContactDetail()));
    }
}
