package hypercell.intern.java_bookcatalogorderservice.dto;

import hypercell.intern.java_bookcatalogorderservice.model.User;

import java.time.ZonedDateTime;

public abstract class BookDto {
    public record Request(String title, String isbn, String author, Double price, Integer availableQuantity,
                          Long createdByID) {
    }

    public record Response(Long id,
                           String title,
                           String isbn,
                           String author,
                           Double price,
                           Integer availableQuantity,
                           ZonedDateTime createdAt, ZonedDateTime updatedAt,
                           UserDTO.Response user
    ) {
    }

    public record updateRequest(Double price, Integer availableQuantity) {
    }

}
