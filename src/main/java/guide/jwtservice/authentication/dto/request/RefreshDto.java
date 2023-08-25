package guide.jwtservice.authentication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshDto {
    @NotBlank(message = "Refresh token is required.")
    @NotNull
    private String refreshToken;
}
