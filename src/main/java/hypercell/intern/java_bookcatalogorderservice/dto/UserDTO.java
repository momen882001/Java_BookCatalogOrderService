package hypercell.intern.java_bookcatalogorderservice.dto;

import hypercell.intern.java_bookcatalogorderservice.enums.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
            String lastname,

            @NotBlank(message = "Username is required")
            @Size(min = 3, max = 50,
                    message = "Username must be between 3 and 50 characters")
            @Pattern(
                    regexp = "^[a-zA-Z0-9._-]+$",
                    message = "Username can only contain letters, numbers, dots, underscores, and hyphens"
            )
            String username,

            @NotBlank(message = "Password is required")
            @Size(min = 8, max = 100,
                    message = "Password must be between 8 and 100 characters")
            String password,

            @NotNull(message = "Role is required")
            UserRoleEnum role

    ) {
    }

    public record Response(
            Long id,
            String firstname,
            String lastname,
            ZonedDateTime createdAt,
            List<BookDto.Response> books,
            String username,
            UserRoleEnum role
    ) {
    }
}