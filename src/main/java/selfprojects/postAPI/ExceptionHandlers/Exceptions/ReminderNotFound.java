package selfprojects.postAPI.ExceptionHandlers.Exceptions;

public class ReminderNotFound extends RuntimeException {
    public ReminderNotFound(String message) {
        super(message);
    }
}
