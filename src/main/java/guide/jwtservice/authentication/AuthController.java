package guide.jwtservice.authentication;

import guide.jwtservice.authentication.dto.request.RegisterDto;
import guide.jwtservice.authentication.service.AuthService;
import guide.jwtservice.utils.HttpResponse;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Data
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public HttpResponse<Object> register(@RequestBody @Validated RegisterDto body) {
        this.authService.register(body.getEmail(), body.getPassword(), body.getConfirmationPassword(), body.getFirstName(), body.getLastName());
        return new HttpResponse<>(true, "User successfully registered.", null);
    }
}
