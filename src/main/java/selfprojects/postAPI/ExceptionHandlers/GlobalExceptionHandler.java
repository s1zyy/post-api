package selfprojects.postAPI.ExceptionHandlers;


import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import selfprojects.postAPI.ExceptionHandlers.Exceptions.PostWithIdNotFound;
import selfprojects.postAPI.ExceptionHandlers.Exceptions.ReminderNotFound;
import selfprojects.postAPI.ExceptionHandlers.Exceptions.UserIsPresentException;
import selfprojects.postAPI.ExceptionHandlers.Exceptions.UserNotFoundException;
import selfprojects.postAPI.Model.RequestsResponses.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return handleNotFoundException(ex);
    }

    @ExceptionHandler(PostWithIdNotFound.class)
    public ResponseEntity<ErrorResponse> handlePostWithIdNotFound(PostWithIdNotFound ex) {
        return handleNotFoundException(ex);
    }


    @ExceptionHandler(ReminderNotFound.class)
    public ResponseEntity<ErrorResponse> handleReminderNotFound(ReminderNotFound ex) {
        return handleNotFoundException(ex);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseError(DataAccessException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Database error: " + ex.getMostSpecificCause().getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericError(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error: " + ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserIsPresentException.class)
    public ResponseEntity<ErrorResponse> handleUserIsPresent(UserIsPresentException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError != null ?
                fieldError.getDefaultMessage() :
                "Invalid request";
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}