package hypercell.intern.java_bookcatalogorderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class UserDTO {

    public record Request(

            @NotBlank(message = "First name is required")
            @Size(min = 2, max = 50,
                    message = "First name must be between 2 and 50 characters")
            @Pattern(
                    regexp = "^[a-zA-Z\\s'-]+$",
                    message = "First name can only contain letters, spaces, hyphens, and apostrophes"
            )
            String firstname,

            @NotBlank(message = "Last name is required")
            @Size(min = 2, max = 50,
                    message = "Last name must be between 2 and 50 characters")
            @Pattern(
                    regexp = "^[a-zA-Z\\s'-]+$",
                    message = "Last name can only contain letters, spaces, hyphens, and apostrophes"
            )
            String lastname

    ) {
    }

    public record Response(
            Long id,
            String firstname,
            String lastname,
            ZonedDateTime createdAt,
            List<BookDto.Response> books
    ) {
    }
}