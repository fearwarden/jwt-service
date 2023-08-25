package guide.jwtservice.authentication.service.implementations;

import guide.jwtservice.authentication.dto.response.TokensResponse;
import guide.jwtservice.authentication.exceptions.throwables.RefreshTokenNotFoundException;
import guide.jwtservice.authentication.service.AuthService;
import guide.jwtservice.authentication.service.JwtService;
import guide.jwtservice.users.exceptions.throwables.PasswordDidNotMatchException;
import guide.jwtservice.users.exceptions.throwables.UserExistException;
import guide.jwtservice.users.exceptions.throwables.UserNotFoundException;
import guide.jwtservice.users.models.Token;
import guide.jwtservice.users.models.User;
import guide.jwtservice.users.repositories.TokenRepository;
import guide.jwtservice.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(String email, String password, String confirmationPassword, String firstName, String lastName) {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);

        // Check if user exist in the database
        if (optionalUser.isPresent()) {
            throw new UserExistException();
        }

        // Check if provided password did not match (this should be also checked on the client side)
        // just additional security
        if (!password.equals(confirmationPassword)) {
            throw new PasswordDidNotMatchException();
        }

        // Create new User and set its information
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(this.passwordEncoder.encode(password));
        this.userRepository.save(user);

        Token refreshToken = new Token();
        refreshToken.setRefreshToken(this.jwtService.generateRefreshToken());
        refreshToken.setUser(user);
        this.tokenRepository.save(refreshToken);
    }

    @Override
    public TokensResponse login(String email, String password) {
        // Attempt to authenticate the user using Spring Security's AuthenticationManager with the given email and password.
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        String accessToken = this.jwtService.generateToken(user);
        Token token = this.tokenRepository.findByUser(user);
        TokensResponse loginResponse = new TokensResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(token.getRefreshToken());
        return loginResponse;
    }

    @Override
    public TokensResponse refresh(String refreshToken) {
        Token token = this.tokenRepository.findByRefreshToken(refreshToken).orElseThrow(RefreshTokenNotFoundException::new);

        String accessToken = this.jwtService.generateToken(token.getUser());
        String newRefreshToken = this.jwtService.generateRefreshToken();
        token.setRefreshToken(newRefreshToken);
        this.tokenRepository.save(token);

        TokensResponse loginResponse = new TokensResponse();
        loginResponse.setRefreshToken(newRefreshToken);
        loginResponse.setAccessToken(accessToken);
        return loginResponse;
    }
}
