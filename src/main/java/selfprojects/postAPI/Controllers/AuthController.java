package selfprojects.postAPI.Controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selfprojects.postAPI.Model.RequestsResponses.AuthRequest;
import selfprojects.postAPI.Model.RequestsResponses.AuthResponse;
import selfprojects.postAPI.Services.AuthService;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){ return authService.login(request); }
}
