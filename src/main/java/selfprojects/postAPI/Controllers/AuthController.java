package selfprojects.postAPI.Controllers;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selfprojects.postAPI.Configs.JwtUtil;
import selfprojects.postAPI.Model.AuthRequest;
import selfprojects.postAPI.Model.AuthResponse;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.MyUserDetails;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;


    public AuthController(JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();

        UserEntity userEntity = user.getUserEntity();

        String token = jwtUtil.generateToken(user.getId());



        return new AuthResponse(token, userEntity);

    }
}
