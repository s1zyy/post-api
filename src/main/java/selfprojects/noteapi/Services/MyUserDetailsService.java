package selfprojects.noteapi.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import selfprojects.noteapi.Model.UserEntity;
import selfprojects.noteapi.MyUserDetails;
import selfprojects.noteapi.Repository.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.map(MyUserDetails::new).orElseThrow(()-> new UsernameNotFoundException(username + " not found"));
    }
}
