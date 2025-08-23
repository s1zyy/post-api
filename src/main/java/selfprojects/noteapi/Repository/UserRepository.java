package selfprojects.noteapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import selfprojects.noteapi.Model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
