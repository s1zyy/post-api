package selfprojects.postAPI.Services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.ExceptionHandlers.Exceptions.UserIsPresentException;
import selfprojects.postAPI.ExceptionHandlers.Exceptions.UserNotFoundException;
import selfprojects.postAPI.Model.Entity.CodeEntity;
import selfprojects.postAPI.Model.RequestsResponses.*;
import selfprojects.postAPI.Model.Entity.ChangeBirthdayRequest;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.Repository.CodeRepository;
import selfprojects.postAPI.Repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final CodeRepository codeRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, CodeRepository codeRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.codeRepository = codeRepository;
        this.authService = authService;
    }

    public UserEntity updateName(ChangeNameRequest changeNameRequest, Long userId) {
        UserEntity user = findById(userId);

        user.setUsername(changeNameRequest.username());

        return userRepository.save(user);
    }
    public void createUser(CreateUserRequest userRequest){

        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()){ throw new UserIsPresentException("User with this email already exists");
        }
        UserEntity userEntity = UserEntity.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role("ROLE_USER")
                .build();
        userRepository.save(userEntity);
    }

    public UserEntity updateBirthDate(ChangeBirthdayRequest request, Long id) {

        UserEntity user = findById(id);
        user.setBirthDate(request.birthDate());
        return userRepository.save(user);
    }

    public TrueFalseResponse sendPasswordRequest(String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            return new TrueFalseResponse(false);
        }
        emailService.sendPasswordReset(email, user.get());

        return new TrueFalseResponse(true);

    }

    public TrueFalseResponse confirmCodeRequest(ConfirmCodeRequest confirmCodeRequest) {
        UserEntity userEntity = findByEmail(confirmCodeRequest.email());

        List<CodeEntity> code =  codeRepository.findByUserId(userEntity.getId());

        for(CodeEntity codeEntity : code){
            if (codeEntity.getCode().equals(confirmCodeRequest.code()) && codeEntity.getExpiryDate().after(new Date())) {
                codeRepository.delete(codeEntity);
                return new TrueFalseResponse(true);
            } else if (codeEntity.getExpiryDate().before(new Date())) {
                codeRepository.delete(codeEntity);
            }
        }
        return new TrueFalseResponse(false);
    }

    public AuthResponse updatePassword(UpdatePasswordRequest updatePasswordRequest){

        UserEntity userEntity = findByEmail(updatePasswordRequest.email());
        userEntity.setPassword(passwordEncoder.encode(updatePasswordRequest.password()));
        userRepository.save(userEntity);
        AuthRequest authRequest = new AuthRequest(updatePasswordRequest.email(), updatePasswordRequest.password());
        return authService.login(authRequest);


    }


    public UserEntity findById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User with this ID not found"));
    }
    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User with this email not found"));
    }
}
