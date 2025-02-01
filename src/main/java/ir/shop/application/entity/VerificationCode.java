package ir.shop.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String code;
    String contactDetail;
    LocalDateTime expiryDate;

    public VerificationCode(String code, String contactDetail, LocalDateTime expiryDate) {
        this.code = code;
        this.contactDetail = contactDetail;
        this.expiryDate = expiryDate;
    }
}
