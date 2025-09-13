package selfprojects.postAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Model.Entity.UserEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByTitleAndContentAndUser(String title, String content, UserEntity user);
    Optional<List<PostEntity>> findAllByUserId(Long userId);
}
