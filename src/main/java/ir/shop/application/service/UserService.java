package ir.shop.application.service;

import ir.shop.application.entity.Role;
import ir.shop.application.entity.User;
import ir.shop.application.service.base.BaseService;
import ir.shop.application.service.payload.request.SignUpRequest;
import ir.shop.application.service.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface UserService extends BaseService<User, Long> {

    void addModerator(String password);

    ResponseEntity<MessageResponse> existControl(SignUpRequest signUpRequest);

    void assignRole(User user, Set<String> roles);
}
