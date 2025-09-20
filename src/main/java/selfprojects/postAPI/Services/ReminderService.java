package selfprojects.postAPI.Services;

import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Model.Entity.ReminderEntity;
import selfprojects.postAPI.Repository.ReminderRepository;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;

    }

    public ReminderEntity saveReminder(PostEntity postEntity) {
        ReminderEntity reminder = postEntity.getReminder();
        reminder.setPost(postEntity);

        reminderRepository.save(reminder);

        return reminder;


    }

    public ReminderEntity updateReminder(PostEntity postEntity) {
        ReminderEntity reminder = reminderRepository.findByPostId(postEntity.getId());
        reminder.setReminderTime(postEntity.getReminder().getReminderTime());
        return reminderRepository.save(reminder);
    }
}
