package selfprojects.postAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Model.Entity.UserEntity;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> { }