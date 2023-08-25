package guide.jwtservice.authentication.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
