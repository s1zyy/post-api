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

    public EmailService(JavaMailSender javaMailSender, CodeRepository codeRepository) {
        this.javaMailSender = javaMailSender;
        this.codeRepository = codeRepository;
    }

    public void sendReminder(ReminderEntity reminder) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(reminder.getPost().getUser().getEmail());
            message.setSubject("Your Reminder");
            message.setText("You have a reminder for: " + reminder.getPost().getTitle());

            javaMailSender.send(message);
            System.out.println("Reminder sent to: " + reminder.getPost().getUser().getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending reminder to: " + reminder.getPost().getUser().getEmail());
        }
    }

    public void sendPasswordReset(String email, UserEntity user) {

        Optional<CodeEntity> checkForExistingCode = codeRepository.findByUserId(user.getId()).stream().findAny();
        if(checkForExistingCode.isPresent()){
            codeRepository.delete(checkForExistingCode.get());
        }

        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6-значный код
        String codeString = String.valueOf(code);

        Date expiryDate = new Date(System.currentTimeMillis() + 1000 * 60 * 30);


        CodeEntity codeEntity = CodeEntity.builder()
                .user(user)
                .code(codeString)
                .expiryDate(expiryDate)
                .build();


        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset");
            message.setText("Your code for resetting your password is: " + codeString);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending password reset to: " + email);
        }
        codeRepository.save(codeEntity);
    }
}
