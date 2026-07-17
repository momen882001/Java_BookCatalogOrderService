package hypercell.intern.java_bookcatalogorderservice.dto;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class OrderDTO {
    public record Request(Long userId, List<OrderItemDTO.Request> orderItems) {
    }

    public record Response(Long id, Long userId, Double totalAmount, List<OrderItemDTO.Request> orderItems,
                           ZonedDateTime createdAt, String status) {
    }

}
