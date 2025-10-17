package selfprojects.postAPI.Services;

import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Model.Entity.ReminderEntity;
import selfprojects.postAPI.Repository.PostRepository;
import selfprojects.postAPI.Repository.ReminderRepository;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final PostRepository postRepository;

    public ReminderService(ReminderRepository reminderRepository, PostRepository postRepository) {
        this.reminderRepository = reminderRepository;
        this.postRepository = postRepository;
    }

    public ReminderEntity saveReminder(PostEntity postEntity) {
        ReminderEntity reminder = postEntity.getReminder();
        reminder.setPost(postEntity);
        reminderRepository.save(reminder);
        return reminder;
    }

    public ReminderEntity updateReminder(PostEntity postEntity) {
        ReminderEntity reminder = reminderRepository.findByPostId(postEntity.getId())
                .orElseThrow(()-> new IllegalStateException("Reminder not found!"));
        reminder.setReminderTime(postEntity.getReminder().getReminderTime());
        return reminderRepository.save(reminder);
    }

    public PostEntity deleteReminder(PostEntity postEntity) {
        ReminderEntity reminder = reminderRepository.findByPostId(postEntity.getId())
                .orElseThrow(()-> new IllegalStateException("Reminder not found!"));
        postEntity.setReminder(null);
        postRepository.save(postEntity);
        reminderRepository.delete(reminder);
        postEntity.setReminder(null);
        return postEntity;
    }
}