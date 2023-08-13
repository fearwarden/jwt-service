package guide.jwtservice.authentication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull
    @NotEmpty
    @Email(message = "Email is invalid.")
    private String email;
    @NotBlank(message = "Password is required.")
    @NotNull
    private String password;
}
