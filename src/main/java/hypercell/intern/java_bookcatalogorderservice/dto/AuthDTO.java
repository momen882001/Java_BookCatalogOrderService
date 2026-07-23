package hypercell.intern.java_bookcatalogorderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public abstract class AuthDTO {

    public record Request(

            @NotBlank(message = "Username is required")
            @Size(min = 3, max = 50,
                    message = "Username must be between 3 and 50 characters")
            String username,

            @NotBlank(message = "Password is required")
            @Size(min = 8, max = 100,
                    message = "Password must be between 8 and 100 characters")
            String password

    ) {
    }

    public record Response(String token) {
    }
}