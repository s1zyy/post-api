package selfprojects.postAPI.Services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Configs.JwtUtil;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.Model.RequestsResponses.AuthRequest;
import selfprojects.postAPI.Model.RequestsResponses.AuthResponse;
import selfprojects.postAPI.MyUserDetails;

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()) );
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        UserEntity userEntity = user.getUserEntity();
        String token = jwtUtil.generateToken(user.getId());
        return new AuthResponse(token, userEntity);
    }

    public MyUserDetails getCurrentUser(){
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getCurrentUserId(){
        return getCurrentUser().getId();
    }

}