package selfprojects.postAPI.Services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.Entity.CodeEntity;
import selfprojects.postAPI.Model.RequestsResponses.*;
import selfprojects.postAPI.Model.Entity.ChangeBirthdayRequest;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.Repository.CodeRepository;
import selfprojects.postAPI.Repository.UserRepository;

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
        UserEntity user = userRepository.findById(userId).orElseThrow();

        user.setUsername(changeNameRequest.username());

        return userRepository.save(user);
    }

    public boolean createUser(CreateUserRequest userRequest){

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setRole("ROLE_USER");

        Optional<UserEntity> checkForUser = userRepository.findByEmail(userEntity.getEmail());
        if(checkForUser.isPresent()){
            return false;
        }

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return true;
    }

    public UserEntity updateBirthDate(ChangeBirthdayRequest request, Long id) {

        UserEntity user = userRepository.findById(id).orElseThrow();

        user.setBirthDate(request.birthDate());
        return userRepository.save(user);
    }

    public TrueFalseResponse sendPasswordRequest(String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            return new TrueFalseResponse(false);
        }
        //send actual email
        emailService.sendPasswordReset(email, user.get());

        return new TrueFalseResponse(true);

    }

    public TrueFalseResponse confirmCodeRequest(ConfirmCodeRequest confirmCodeRequest) {
        UserEntity userEntity = userRepository.findByEmail(confirmCodeRequest.email()).orElseThrow();

        List<CodeEntity> code =  codeRepository.findByUserId(userEntity.getId());

        for(CodeEntity codeEntity : code){
            if (codeEntity == null) {
                return new TrueFalseResponse(false);
            } else if (!codeEntity.getCode().equals(confirmCodeRequest.code())) {
                return new TrueFalseResponse(false);
            } else if (codeEntity.getExpiryDate().before(new java.util.Date())) {
                codeRepository.delete(codeEntity);
                return new TrueFalseResponse(false);
            } else if (codeEntity.getCode().equals(confirmCodeRequest.code()) && codeEntity.getExpiryDate().after(new java.util.Date())) {
                codeRepository.delete(codeEntity);
                return new TrueFalseResponse(true);
            }
        }

      return new TrueFalseResponse(false);
    }

    public AuthResponse updatePassword(UpdatePasswordRequest updatePasswordRequest){

        UserEntity userEntity = userRepository.findByEmail(updatePasswordRequest.email()).orElseThrow();
        userEntity.setPassword(passwordEncoder.encode(updatePasswordRequest.password()));
        userRepository.save(userEntity);
        AuthRequest authRequest = new AuthRequest(updatePasswordRequest.email(), updatePasswordRequest.password());
        return authService.login(authRequest);


    }
}
