package hypercell.intern.java_bookcatalogorderservice.dto;

import java.time.ZonedDateTime;

public abstract class BookDto {
    public record Request(String title, String isbn, String author, Double price, Integer availableQuantity) {
    }

    public record Response(Long id,
                           String title,
                           String isbn,
                           String author,
                           Double price,
                           Integer availableQuantity,
                           ZonedDateTime createdAt, ZonedDateTime updatedAt) {
    }

    public record updateRequest(Double price, Integer availableQuantity) {
    }

}
