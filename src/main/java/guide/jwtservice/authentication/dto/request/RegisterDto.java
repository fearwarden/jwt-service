package guide.jwtservice.authentication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDto {
    @NotNull
    @NotEmpty
    @Email(message = "Email is invalid.")
    private String email;
    @NotBlank(message = "First Name is required.")
    @NotNull
    private String firstName;
    @NotBlank(message = "Last Name is required.")
    @NotNull
    private String lastName;
    @NotBlank(message = "Password is required.")
    @NotNull
    private String password;
    @NotBlank(message = "Confirmation Password is required.")
    @NotNull
    private String confirmationPassword;
}
