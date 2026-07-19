package hypercell.intern.java_bookcatalogorderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public abstract class OrderItemDTO {

    public record Request(

            @NotNull(message = "Book id is required")
            @Positive(message = "Book id must be greater than 0")
            Long bookId,

            @NotNull(message = "Quantity is required")
            @Positive(message = "Quantity must be greater than 0")
            Integer quantity

    ) {
    }

    public record Response(
            Long id,
            Long orderID,
            Long bookId,
            Integer quantity,
            Double unitPrice,
            Double subTotal,
            BookDto.Response book
    ) {
    }
}