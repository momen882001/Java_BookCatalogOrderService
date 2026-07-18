package hypercell.intern.java_bookcatalogorderservice.mapper;

import hypercell.intern.java_bookcatalogorderservice.dto.OrderDTO;
import hypercell.intern.java_bookcatalogorderservice.dto.OrderItemDTO;
import hypercell.intern.java_bookcatalogorderservice.model.Book;
import hypercell.intern.java_bookcatalogorderservice.model.Order;
import hypercell.intern.java_bookcatalogorderservice.model.User;

public class OrderMapper {

    public static OrderDTO.Response toResponse(Order order) {
        return new OrderDTO.Response(
                order.getId(),
                order.getCreatedBy().getId(),
                order.getTotalAmount(),
                order.getOrderItems().stream()
                        .map(OrderItemMapper::toResponse)
                        .toList(),
                order.getCreatedAt(),
                order.getStatus()
        );
    }
}
