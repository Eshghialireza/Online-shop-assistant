package ir.shop.application.service.impl;

import ir.shop.application.entity.*;
import ir.shop.application.exception.EndLoopException;
import ir.shop.application.repository.UserRepo;
import ir.shop.application.service.UserService;
import ir.shop.application.service.VendorService;
import ir.shop.application.service.base.BaseServiceImpl;
import ir.shop.application.service.payload.request.SignUpRequest;
import ir.shop.application.service.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepo> implements UserService {
    public UserServiceImpl(UserRepo repo) {
        super(repo);
    }

    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    VendorService vendorService;
    @Autowired
    CustomerServiceImpl customerService;

    @Override

    public void addModerator(String encodedPassword) {
        if (!repo.existsByUsername("modir")) {
            Role adminRole = roleService.findByName(ERoles.VENDOR);
            Set<Role> roles = new HashSet<>(Collections.singletonList(adminRole));
            User manager = new User("modir", encodedPassword, "09919811164", true, roles);
            save(manager);
        }

    }

    @Override
    public ResponseEntity<MessageResponse> existControl(SignUpRequest signUpRequest) {
        if (repo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (repo.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: PhoneNumber is already in use!"));
        }
        if (repo.existsByMailAddress(signUpRequest.getMailAddress())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Mail address is already in use!"));
        }
        return null;
    }

    @Override
    public void assignRole(User user, Set<String> roles) {
        Set<Role> userRoles = new HashSet<>();
        if (roles == null) {
            Role userRole = roleService.findByName(ERoles.USER);
            userRoles.add(userRole);
            user.setRole(userRole);
            createCustomerForUser(user);
            throw new EndLoopException("User created successfully!");
        } else {
            roles.forEach(role -> {
                role = role.toUpperCase();
                switch (role) {
                    case "VENDOR": {
                        Role roleVendor = roleService.findByName(ERoles.VENDOR);
                        userRoles.add(roleVendor);
                        user.setRole(roleVendor);
                        createVendorForUser(user);
                        throw new EndLoopException("Vendor created successfully!");
                    }
                    case "USER": {
                        Role roleUser = roleService.findByName(ERoles.USER);
                        userRoles.add(roleUser);
                        user.setRole(roleUser);
                        createCustomerForUser(user);
                        throw new EndLoopException("User created successfully!");
                    }
                    default:
                        Role userRole = roleService.findByName(ERoles.USER);
                        userRoles.add(userRole);
                        user.setRole(userRole);
                        createCustomerForUser(user);
                        throw new EndLoopException("User created successfully!");
                }
            });
        }
    }

    private void createVendorForUser(User user) {
        Vendor vendor = new Vendor();
        vendor.setUser(user);
        vendorService.save(vendor);
    }

    private void createCustomerForUser(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        customerService.save(customer);
    }
}
