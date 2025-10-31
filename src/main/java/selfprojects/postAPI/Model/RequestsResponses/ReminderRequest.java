package selfprojects.postAPI.Model.RequestsResponses;

import java.time.LocalDateTime;

public record ReminderRequest(LocalDateTime reminderTime, Long postId) {
}
