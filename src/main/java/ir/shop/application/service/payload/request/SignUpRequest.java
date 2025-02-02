package ir.shop.application.service.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String mailAddress;
    @NotBlank
    @Size(min = 11, max = 11)
    private String phoneNumber;

    private Set<String> role;

    private String validateCode;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}
