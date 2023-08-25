package guide.jwtservice.authentication.service;

import guide.jwtservice.authentication.dto.response.TokensResponse;

public interface AuthService {
    void register(String email, String password, String confirmationPassword, String firstName, String lastName);
    TokensResponse login(String email, String password);
    TokensResponse refresh(String refreshToken);
}
