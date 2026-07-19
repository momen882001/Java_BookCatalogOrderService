package hypercell.intern.java_bookcatalogorderservice.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ErrorResponseDTO(Object message, HttpStatus status, Integer statusCode, ZonedDateTime timestamp) {
}
