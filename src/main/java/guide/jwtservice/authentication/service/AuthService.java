package guide.jwtservice.authentication.service;

public interface AuthService {
    void register(String email, String password, String confirmationPassword, String firstName, String lastName);
    String login(String email, String password);
}
