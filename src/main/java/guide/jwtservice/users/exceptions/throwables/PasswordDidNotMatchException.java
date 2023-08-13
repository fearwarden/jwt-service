package guide.jwtservice.users.exceptions.throwables;

public class PasswordDidNotMatchException extends RuntimeException {
    public PasswordDidNotMatchException() {
        super("Password did not match.");
    }
}
