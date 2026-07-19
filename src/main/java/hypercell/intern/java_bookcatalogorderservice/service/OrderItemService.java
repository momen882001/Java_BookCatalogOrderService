package hypercell.intern.java_bookcatalogorderservice.service;

import hypercell.intern.java_bookcatalogorderservice.dto.OrderItemDTO;
import hypercell.intern.java_bookcatalogorderservice.exception.NotFoundException;
import hypercell.intern.java_bookcatalogorderservice.mapper.OrderItemMapper;
import hypercell.intern.java_bookcatalogorderservice.model.Book;
import hypercell.intern.java_bookcatalogorderservice.model.Order;
import hypercell.intern.java_bookcatalogorderservice.model.OrderItem;
import hypercell.intern.java_bookcatalogorderservice.repository.BookRepository;
import hypercell.intern.java_bookcatalogorderservice.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;

    public OrderItemDTO.Response createOrderItem(
            OrderItemDTO.Request request,
            Order order) {

        Book book = getBook(request.bookId());

        decreaseBookQuantity(book, request.quantity());

        OrderItem orderItem = saveOrderItem(request, order, book);

        return OrderItemMapper.toResponse(orderItem);
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new NotFoundException("Book not found with id: " + bookId));
    }

    private void decreaseBookQuantity(Book book, Integer quantity) {
        book.setAvailableQuantity(book.getAvailableQuantity() - quantity);
        bookRepository.save(book);
    }

    private OrderItem saveOrderItem(
            OrderItemDTO.Request request,
            Order order,
            Book book) {

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .book(book)
                .quantity(request.quantity())
                .unitPrice(book.getPrice())
                .subTotal(book.getPrice() * request.quantity())
                .build();

        return orderItemRepository.save(orderItem);
    }
}
