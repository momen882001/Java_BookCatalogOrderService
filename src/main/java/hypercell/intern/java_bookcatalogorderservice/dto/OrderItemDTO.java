package hypercell.intern.java_bookcatalogorderservice.dto;

public abstract class OrderItemDTO {
    public record Request(Long bookId, Integer quantity) {
    }

    public record Response(Long id, Long orderID, Long bookId, Integer quantity, Double unitPrice, Double subTotal) {
    }
}
