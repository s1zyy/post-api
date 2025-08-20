package selfprojects.noteapi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import selfprojects.noteapi.Model.NoteEntity;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    Optional<NoteEntity> findByTitleAndContentAndUsername(String title, String content, String username);
}
