package selfprojects.postAPI.ExceptionHandlers.Exceptions;

public class PostWithIdNotFound extends RuntimeException {
    public PostWithIdNotFound(String message) {
        super(message);
    }
}
