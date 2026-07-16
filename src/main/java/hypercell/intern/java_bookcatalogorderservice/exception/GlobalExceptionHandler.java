package hypercell.intern.java_bookcatalogorderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(ValidationException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getMessage(),
                status,
                400,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(NotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getMessage(),
                status,
                404,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}
