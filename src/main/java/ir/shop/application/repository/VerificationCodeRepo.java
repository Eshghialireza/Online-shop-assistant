package ir.shop.application.repository;

import ir.shop.application.entity.VerificationCode;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepo extends BaseRepo<VerificationCode, Long> {
    VerificationCode findByContactDetail(String contactDetail);
}
