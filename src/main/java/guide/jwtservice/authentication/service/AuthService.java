package guide.jwtservice.authentication.service;

import guide.jwtservice.authentication.dto.response.LoginResponse;

public interface AuthService {
    void register(String email, String password, String confirmationPassword, String firstName, String lastName);
    LoginResponse login(String email, String password);
    LoginResponse refresh(String refreshToken);
}
