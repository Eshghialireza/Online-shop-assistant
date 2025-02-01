package ir.shop.application.controllers;

import ir.shop.application.entity.*;
import ir.shop.application.exception.InvalidVerificationCodeException;
import ir.shop.application.repository.RoleRepo;
import ir.shop.application.repository.UserRepo;
import ir.shop.application.service.ConfigService;
import ir.shop.application.service.CustomerService;
import ir.shop.application.service.UserService;
import ir.shop.application.service.VendorService;
import ir.shop.application.service.impl.MailServiceImpl;
import ir.shop.application.service.impl.VerificationCodeServiceImpl;
import ir.shop.application.service.payload.request.LoginRequest;
import ir.shop.application.service.payload.request.SignUpRequest;
import ir.shop.application.service.payload.response.JwtResponse;
import ir.shop.application.service.payload.response.MessageResponse;
import ir.shop.application.service.security.jwt.JwtUtils;
import ir.shop.application.service.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepository;
    @Autowired
    UserService userService;
    @Autowired
    VendorService vendorService;
    @Autowired
    VerificationCodeServiceImpl verificationCodeService;
    @Autowired
    ConfigService configService;
    @Autowired
    CustomerService customerService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    MailServiceImpl mailServiceImpl;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPhoneNumber(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.existControl(signUpRequest);
        if (verificationCodeService.validateCode(signUpRequest.getValidateCode(), signUpRequest)) {
            // Create new user's account
            userService.assignRole(new User(signUpRequest.getUsername(),
                    signUpRequest.getMailAddress(),
                    encoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getPhoneNumber()), signUpRequest.getRole());

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
        throw new InvalidVerificationCodeException("Invalid or expired verification code!");
    }


}
