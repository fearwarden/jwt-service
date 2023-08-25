package guide.jwtservice.users.repositories;

import guide.jwtservice.users.models.Token;
import guide.jwtservice.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    Token findByUser(User user);
    Optional<Token> findByRefreshToken(String refreshToken);
}
