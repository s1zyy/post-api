package selfprojects.postAPI.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.MyUserDetails;
import selfprojects.postAPI.Repository.UserRepository;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(MyUserDetails::new).orElseThrow(()-> new UsernameNotFoundException(email + " not found"));
    }

    public UserDetails loadUserById(Long id){
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(MyUserDetails::new).orElseThrow(()-> new UsernameNotFoundException(id + " not found"));

    }
}
