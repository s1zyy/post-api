package selfprojects.postAPI.Services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.ChangeNameRequest;
import selfprojects.postAPI.Model.CreateUserRequest;
import selfprojects.postAPI.Model.Entity.ChangeBirthdayRequest;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.Repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}
