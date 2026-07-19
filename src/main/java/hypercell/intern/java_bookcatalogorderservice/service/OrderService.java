package hypercell.intern.java_bookcatalogorderservice.service;

import hypercell.intern.java_bookcatalogorderservice.dto.OrderDTO;
import hypercell.intern.java_bookcatalogorderservice.dto.OrderItemDTO;
import hypercell.intern.java_bookcatalogorderservice.enums.OrderStatusEnum;
import hypercell.intern.java_bookcatalogorderservice.exception.NotFoundException;
import hypercell.intern.java_bookcatalogorderservice.exception.ValidationException;
import hypercell.intern.java_bookcatalogorderservice.mapper.OrderMapper;
import hypercell.intern.java_bookcatalogorderservice.model.Book;
import hypercell.intern.java_bookcatalogorderservice.model.Order;
import hypercell.intern.java_bookcatalogorderservice.model.User;
import hypercell.intern.java_bookcatalogorderservice.repository.BookRepository;
import hypercell.intern.java_bookcatalogorderservice.repository.OrderRepository;
import hypercell.intern.java_bookcatalogorderservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public OrderDTO.Response createOrder(OrderDTO.Request orderRequest) {

        User user = getUser(orderRequest.userId());

        double totalAmount = validateOrderAndCalculateTotal(orderRequest);

        Order savedOrder = saveOrder(user, totalAmount);

        List<OrderItemDTO.Response> orderItems =
                createOrderItem(savedOrder, orderRequest.orderItems());

        return new OrderDTO.Response(
                savedOrder.getId(),
                user.getId(),
                savedOrder.getTotalAmount(),
                orderItems,
                savedOrder.getCreatedAt(),
                savedOrder.getStatus()
        );
    }

    public OrderDTO.Response getByIdWithOrderItems(Long id) {
        Order order = orderRepository.findByIdWithOrderItems(id).orElseThrow(
                () -> new NotFoundException("Order not found with id: " + id));
        return OrderMapper.toResponse(order);
    }

    public List<OrderDTO.Response> getAllWithOrderItems() {
        List<Order> orders = orderRepository.findAllWithOrderItems();
        return orders.stream()
                .map(OrderMapper::toResponse)
                .toList();
    }


    public OrderDTO.Response cancelOrder(Long id) {
        Order order = orderRepository.findByIdWithOrderItems(id).orElseThrow(
                () -> new NotFoundException("Order not found with id: " + id));


        validateOrderCanBeCancelled(order);

        // update order status from pending to canceled
        order.setStatus(OrderStatusEnum.CANCELLED);
        order.getOrderItems().forEach(
                orderItem -> orderItem.getBook()
                        .setAvailableQuantity(orderItem.getQuantity() + orderItem.getBook().getAvailableQuantity())
        );

        orderRepository.save(order);

        return OrderMapper.toResponse(order);

    }

//  ------------------------------- Private Methods ---------------------------------------------------

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("User with id " + userId + " not found"));
    }

    private double validateOrderAndCalculateTotal(OrderDTO.Request orderRequest) {

        Set<Long> bookIds = new HashSet<>();
        double total = 0;

        for (OrderItemDTO.Request item : orderRequest.orderItems()) {

            validateDuplicateBook(bookIds, item.bookId());

            Book book = getBook(item.bookId());

            validateBookAvailability(book, item.quantity());

            total += book.getPrice() * item.quantity();
        }

        return total;
    }

    private void validateDuplicateBook(Set<Long> bookIds, Long bookId) {

        if (!bookIds.add(bookId)) {
            throw new ValidationException(
                    "Duplicate book with id " + bookId + " in the order");
        }
    }

    private Book getBook(Long bookId) {

        return bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new NotFoundException("Book not found with id: " + bookId));
    }

    private void validateBookAvailability(Book book, Integer requestedQuantity) {

        if (book.getAvailableQuantity() <= 0) {
            throw new ValidationException(
                    "Book '" + book.getTitle() + "' is out of stock");
        }

        if (requestedQuantity > book.getAvailableQuantity()) {
            throw new ValidationException(
                    "Requested quantity exceeds available stock for book: "
                            + book.getTitle());
        }
    }

    private Order saveOrder(User user, double totalAmount) {

        Order order = Order.builder()
                .createdBy(user)
                .createdAt(ZonedDateTime.now(ZoneId.of("Z")))
                .status(OrderStatusEnum.PENDING)
                .totalAmount(totalAmount)
                .build();

        return orderRepository.save(order);
    }

    private List<OrderItemDTO.Response> createOrderItem(
            Order order,
            List<OrderItemDTO.Request> requests) {

        return requests.stream()
                .map(request -> orderItemService.createOrderItem(request, order))
                .toList();
    }


    // validate order which not in pending status
    private void validateOrderCanBeCancelled(Order order) {
        if (order.getStatus() == OrderStatusEnum.CANCELLED) {
            throw new ValidationException("Order already cancelled");
        }

        if (order.getStatus() == OrderStatusEnum.COMPLETED) {
            throw new ValidationException("Order already completed");
        }
    }


}