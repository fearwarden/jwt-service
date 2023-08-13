package guide.jwtservice.users.exceptions.throwables;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found.");
    }
}
