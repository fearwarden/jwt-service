package guide.jwtservice.authentication;

import guide.jwtservice.authentication.dto.request.LoginDto;
import guide.jwtservice.authentication.dto.request.RefreshDto;
import guide.jwtservice.authentication.dto.request.RegisterDto;
import guide.jwtservice.authentication.dto.response.LoginResponse;
import guide.jwtservice.authentication.service.AuthService;
import guide.jwtservice.utils.HttpResponse;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public HttpResponse<LoginResponse> login(@RequestBody @Validated LoginDto body) {
        LoginResponse tokens = this.authService.login(body.getEmail(), body.getPassword());
        return new HttpResponse<>(true, "User successfully logged in.", tokens);
    }

    @PostMapping("/refresh")
    public HttpResponse<LoginResponse> refresh(@RequestBody @Validated RefreshDto body) {
        LoginResponse tokens = this.authService.refresh(body.getRefreshToken());
        return new HttpResponse<>(true, "Access token successfully generated.", tokens);
    }
}
