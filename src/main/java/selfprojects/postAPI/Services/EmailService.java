package selfprojects.postAPI.Services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.Entity.ReminderEntity;

@Service
public class EmailService {


    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendReminder(ReminderEntity reminder) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(reminder.getPost().getUser().getEmail());
            message.setSubject("Your Reminder");
            message.setText(reminder.getMessage());

            javaMailSender.send(message);
            System.out.println("Reminder sent to: " + reminder.getPost().getUser().getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending reminder to: " + reminder.getPost().getUser().getEmail());
        }
    }
}
