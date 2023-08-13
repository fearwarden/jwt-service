package guide.jwtservice.users.exceptions.throwables;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super("User exist.");
    }
}
