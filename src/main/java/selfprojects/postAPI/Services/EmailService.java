package selfprojects.postAPI.Services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.Entity.CodeEntity;
import selfprojects.postAPI.Model.Entity.ReminderEntity;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.Repository.CodeRepository;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final CodeRepository codeRepository;

    private final Random random = new Random();

    public EmailService(JavaMailSender javaMailSender, CodeRepository codeRepository) {
        this.javaMailSender = javaMailSender;
        this.codeRepository = codeRepository;
    }

    public void sendReminder(ReminderEntity reminder) {
        sendEmail(reminder.getPost().getUser().getEmail(),
                "Your Reminder",
                "You have a reminder for: " + reminder.getPost().getTitle());
    }

    public void sendPasswordReset(String email, UserEntity user) {

        Optional<CodeEntity> checkForExistingCode = codeRepository.findByUserId(user.getId()).stream().findAny();
        checkForExistingCode.ifPresent(codeRepository::delete);

        String codeString = getRandomIntToString();

        Date expiryDate = new Date(System.currentTimeMillis() + 1000 * 60 * 30);

        CodeEntity codeEntity = CodeEntity.builder()
                .user(user)
                .code(codeString)
                .expiryDate(expiryDate)
                .build();

        sendEmail(email,
                "Password Reset",
                "Your code for resetting your password is: " + codeString);
        codeRepository.save(codeEntity);
    }

    public void sendEmail(String email, String subject, String text) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending email to: " + email);
        }
    }
    private String getRandomIntToString() {
        return String.valueOf(100000 + random.nextInt(900000));
    }
}