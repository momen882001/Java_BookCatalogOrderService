package hypercell.intern.java_bookcatalogorderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;

public abstract class BookDto {

    public record Request(

            @NotBlank(message = "Title is required")
            @Size(max = 255, message = "Title must not exceed 255 characters")
            String title,

            @NotBlank(message = "ISBN is required")
            @Size(min = 10, max = 20, message = "ISBN must be between 10 and 20 characters")
            String isbn,

            @NotBlank(message = "Author is required")
            @Size(max = 150, message = "Author name must not exceed 255 characters")
            String author,

            @NotNull(message = "Price is required")
            @Positive(message = "Price must be greater than 0")
            Double price,

            @NotNull(message = "Available quantity is required")
            @Min(value = 0, message = "Available quantity cannot be negative")
            Integer availableQuantity,

            @NotNull(message = "Created by user id is required")
            @Positive(message = "Created by user id must be greater than 0")
            Long createdByID

    ) {
    }

    public record Response(
            Long id,
            String title,
            String isbn,
            String author,
            Double price,
            Integer availableQuantity,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt,
            UserDTO.Response createdBy
    ) {
    }

    public record updateRequest(

            @NotNull(message = "Price is required")
            @Positive(message = "Price must be greater than 0")
            Double price,

            @NotNull(message = "Available quantity is required")
            @Min(value = 0, message = "Available quantity cannot be negative")
            Integer availableQuantity

    ) {
    }
}