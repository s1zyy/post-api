package selfprojects.postAPI.Cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import selfprojects.postAPI.Model.Entity.ReminderEntity;
import selfprojects.postAPI.Repository.ReminderRepository;
import selfprojects.postAPI.Services.EmailService;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderScheduler {

    private ReminderRepository reminderRepository;

    private EmailService emailService;

    public ReminderScheduler(ReminderRepository reminderRepository, EmailService emailService) {
        this.reminderRepository = reminderRepository;
        this.emailService = emailService;
    }


    @Scheduled(cron = "0 * * * * *")
    public void checkReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<ReminderEntity> dueReminders = reminderRepository.findDueReminders(now);

        for (ReminderEntity reminder : dueReminders) {
            emailService.sendReminder(reminder);
            reminder.setDone(true);
            reminderRepository.save(reminder);
        }
    }

}
