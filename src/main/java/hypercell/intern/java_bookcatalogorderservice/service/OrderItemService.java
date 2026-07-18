package hypercell.intern.java_bookcatalogorderservice.service;

import hypercell.intern.java_bookcatalogorderservice.dto.OrderItemDTO;
import hypercell.intern.java_bookcatalogorderservice.exception.NotFoundException;
import hypercell.intern.java_bookcatalogorderservice.exception.ValidationException;
import hypercell.intern.java_bookcatalogorderservice.model.Book;
import hypercell.intern.java_bookcatalogorderservice.model.Order;
import hypercell.intern.java_bookcatalogorderservice.model.OrderItem;
import hypercell.intern.java_bookcatalogorderservice.repository.BookRepository;
import hypercell.intern.java_bookcatalogorderservice.repository.OrderItemRepository;
import hypercell.intern.java_bookcatalogorderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderItemDTO.Response createOrderItem(OrderItemDTO.Request orderItemRequest, Order order) {
        Book book = bookRepository.findById(orderItemRequest.bookId())
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + orderItemRequest.bookId()));

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .book(book)
                .quantity(orderItemRequest.quantity())
                .unitPrice(book.getPrice())
                .subTotal(orderItemRequest.quantity() * book.getPrice())
                .build();

        book.setAvailableQuantity(book.getAvailableQuantity() - orderItemRequest.quantity());
        bookRepository.save(book);
        orderItemRepository.save(orderItem);
        return new OrderItemDTO.Response(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getBook().getId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getSubTotal()
        );
    }
}
