package hypercell.intern.java_bookcatalogorderservice.mapper;

import hypercell.intern.java_bookcatalogorderservice.dto.OrderItemDTO;
import hypercell.intern.java_bookcatalogorderservice.model.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO.Response toResponse(OrderItem orderItem) {
        return new OrderItemDTO.Response(orderItem.getId(), orderItem.getOrder().getId(), orderItem.getBook().getId(),
                orderItem.getQuantity(), orderItem.getBook().getPrice(), orderItem.getSubTotal(), BookMapper.toResponse(orderItem.getBook()));
    }
}
