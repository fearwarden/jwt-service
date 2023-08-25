package guide.jwtservice.authentication.exceptions;

import guide.jwtservice.authentication.exceptions.throwables.RefreshTokenNotFoundException;
import guide.jwtservice.utils.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RefreshTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpResponse<Object> refreshTokenNotFoundHandler(RefreshTokenNotFoundException ex) {
        return new HttpResponse<>(false, ex.getMessage(), null);
    }
}
