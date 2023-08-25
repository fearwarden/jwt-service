package guide.jwtservice.authentication.exceptions.throwables;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException() {
        super("Refresh token not found.");
    }
}
