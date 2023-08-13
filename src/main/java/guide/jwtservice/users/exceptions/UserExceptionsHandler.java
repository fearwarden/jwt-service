package guide.jwtservice.users.exceptions;

import guide.jwtservice.users.exceptions.throwables.UserNotFoundException;
import guide.jwtservice.utils.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExceptionsHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpResponse<Object> userNotFoundHandler(UserNotFoundException ex) {
        return new HttpResponse<>(false, ex.getMessage(), null);
    }
}
