package hypercell.intern.java_bookcatalogorderservice.dto;

import hypercell.intern.java_bookcatalogorderservice.enums.OrderStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class OrderDTO {

    public record Request(

            @NotNull(message = "User id is required")
            @Positive(message = "User id must be greater than 0")
            Long userId,

            @Valid
            @NotEmpty(message = "Order must contain at least one item")
            List<OrderItemDTO.Request> orderItems

    ) {
    }

    public record Response(
            Long id,
            Long userId,
            Double totalAmount,
            List<OrderItemDTO.Response> orderItems,
            ZonedDateTime createdAt,
            OrderStatusEnum status
    ) {
    }
}