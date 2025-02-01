package ir.shop.application.repository;

import ir.shop.application.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends BaseRepo<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByMailAddress(String mailAddress);

    boolean existsByPhoneNumber(String phoneNumber);
}
