package selfprojects.postAPI.Services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.MyUserDetails;
import selfprojects.postAPI.Repository.UserRepository;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MyUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(MyUserDetails::new).orElseThrow(()-> new UsernameNotFoundException(email + " not found"));
    }

    public MyUserDetails loadUserById(Long id){
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(MyUserDetails::new).orElseThrow(()-> new UsernameNotFoundException(id + " not found"));
    }
}