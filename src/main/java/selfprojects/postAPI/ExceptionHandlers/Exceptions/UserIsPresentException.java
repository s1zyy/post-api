package selfprojects.postAPI.ExceptionHandlers.Exceptions;

public class UserIsPresentException extends RuntimeException {
    public UserIsPresentException(String message) {
        super(message);
    }
}
