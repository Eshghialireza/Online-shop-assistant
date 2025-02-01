package ir.shop.application.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String mailAddress;
    @Column(nullable = false)
    private String phoneNumber;
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private Set<Role> roles = new HashSet<>();

    public User(String username, String mailAddress, String password, String phoneNumber) {
        this.username = username;
        this.mailAddress = mailAddress;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(String username, String password, String mailAddress, boolean enabled, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.mailAddress = mailAddress;
        this.enabled = enabled;
        this.roles = roles;
    }

    public void setRole(Role role) {
        roles.add(role);
    }
}
