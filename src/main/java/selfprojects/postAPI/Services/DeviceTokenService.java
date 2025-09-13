package selfprojects.postAPI.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.DeviceTokens;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.MyUserDetails;
import selfprojects.postAPI.Repository.DeviceTokensRepository;
import selfprojects.postAPI.Repository.UserRepository;

import java.util.Optional;

@Service
public class DeviceTokenService {


    private final DeviceTokensRepository deviceTokensRepository;
    private final UserRepository userRepository;

    public DeviceTokenService(DeviceTokensRepository deviceTokensRepository, UserRepository userRepository) {
        this.deviceTokensRepository = deviceTokensRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> sendDeviceToken(String deviceToken) {
        MyUserDetails user = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //System.out.println(user);

        Optional<DeviceTokens> deviceTokens = deviceTokensRepository.findByDeviceTokenAndUser_Id(deviceToken, user.getId());


        if(deviceTokens.isPresent()){
            return ResponseEntity.ok("Device token already exists");
        }

        DeviceTokens deviceTokenEntity = new DeviceTokens();
        deviceTokenEntity.setDeviceToken(deviceToken);
        UserEntity userEntity = userRepository.findById(user.getId()).orElse(null);
        deviceTokenEntity.setUser(userEntity);
        deviceTokensRepository.save(deviceTokenEntity);

        return ResponseEntity.ok("Device registered successfully!");


    }

}
