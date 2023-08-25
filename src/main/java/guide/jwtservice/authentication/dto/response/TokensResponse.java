package guide.jwtservice.authentication.dto.response;

import lombok.Data;

@Data
public class TokensResponse {
    private String accessToken;
    private String refreshToken;
}
