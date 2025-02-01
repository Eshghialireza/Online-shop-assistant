package ir.shop.application;

import ir.shop.application.entity.Config;
import ir.shop.application.service.ConfigService;
import ir.shop.application.service.RoleService;
import ir.shop.application.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(RoleService roleService, UserService userService, ConfigService configService, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                configService.createConfig(new Config("config"));
                roleService.addRolesToApplication();
                userService.addModerator(passwordEncoder.encode("modir"));
            } catch (Exception e) {
                System.out.println("ERROR :application tried to save a duplicated entity!");
            }
        };
    }

}
