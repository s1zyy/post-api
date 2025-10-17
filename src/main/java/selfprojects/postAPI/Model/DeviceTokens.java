package selfprojects.postAPI.Model;

import jakarta.persistence.*;
import lombok.Data;
import selfprojects.postAPI.Model.Entity.UserEntity;

@Entity
@Data
public class DeviceTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_token")
    private String deviceToken;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;
}