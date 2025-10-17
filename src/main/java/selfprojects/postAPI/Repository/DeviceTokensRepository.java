package selfprojects.postAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import selfprojects.postAPI.Model.DeviceTokens;
import java.util.Optional;

@Repository
public interface DeviceTokensRepository extends JpaRepository<DeviceTokens, Long> {
    Optional<DeviceTokens> findByDeviceTokenAndUser_Id(String deviceToken, Long userId);
}