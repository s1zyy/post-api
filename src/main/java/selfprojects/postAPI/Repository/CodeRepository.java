package selfprojects.postAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import selfprojects.postAPI.Model.Entity.CodeEntity;
import selfprojects.postAPI.Model.Entity.UserEntity;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
    List<CodeEntity> findByUserId(Long userId);
    Long user(UserEntity user);
}