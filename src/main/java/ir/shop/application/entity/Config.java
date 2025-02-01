package ir.shop.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    //default mode is on Mail
    @Getter
    @Setter
    private UserContactTypes verificationCodeContact = UserContactTypes.MAIL;

    public Config(String name) {
        this.name = name;
    }
}
