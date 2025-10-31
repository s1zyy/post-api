package selfprojects.postAPI.Services;

import org.springframework.stereotype.Service;
import selfprojects.postAPI.ExceptionHandlers.Exceptions.ReminderNotFound;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Model.Entity.ReminderEntity;
import selfprojects.postAPI.Model.RequestsResponses.ReminderRequest;
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

    public ReminderEntity saveReminder(ReminderRequest reminderRequest) {
        PostEntity postEntity = postRepository.findById(reminderRequest.postId()).orElseThrow(
                ()-> new ReminderNotFound("Post with id " + reminderRequest.postId() + " not found"));
        ReminderEntity reminder = ReminderEntity.builder()
                .completed(false)
                .reminderTime(reminderRequest.reminderTime())
                .build();
        reminder.setPost(postEntity);
        reminderRepository.save(reminder);
        return reminder;
    }

    public ReminderEntity updateReminder(ReminderEntity reminderForUpdate) {
        ReminderEntity reminder = getReminderById(reminderForUpdate.getId());
        reminder.setReminderTime(reminderForUpdate.getReminderTime());
        return reminderRepository.save(reminder);
    }

    public void deleteReminder(Long id) {
        ReminderEntity reminder = getReminderById(id);
        PostEntity postEntity = reminder.getPost();
        postEntity.setReminder(null);
        postRepository.save(postEntity);
    }

    public ReminderEntity getReminderById(Long id){
        return reminderRepository.findById(id).orElseThrow(()-> new ReminderNotFound("Reminder with id " + id + " not found"));
    }
}