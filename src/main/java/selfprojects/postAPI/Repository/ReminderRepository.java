package selfprojects.postAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import selfprojects.postAPI.Model.Entity.ReminderEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<ReminderEntity, Long> {
    @Query("SELECT r FROM ReminderEntity r WHERE r.completed = false AND r.reminderTime <= :now")
    List<ReminderEntity> findDueReminders(@Param("now") LocalDateTime now);

    ReminderEntity findByPostId(Long id);
}
