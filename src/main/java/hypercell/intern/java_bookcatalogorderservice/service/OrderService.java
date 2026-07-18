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
        if (orderRequest.orderItems().isEmpty()) {
            throw new ValidationException("Order items cannot be empty");
        }

        User user = userRepository.findById(orderRequest.userId())
                .orElseThrow(() -> new NotFoundException("User with id " + orderRequest.userId() + " not found"));

        Set<Long> bookIds = new HashSet<>();
        Double totalAmount = 0.0;

        for (OrderItemDTO.Request itemRequest : orderRequest.orderItems()) {

            if (!bookIds.add(itemRequest.bookId())) {
                throw new ValidationException(
                        "Duplicate book with id " + itemRequest.bookId() + " in the order");
            }

            Book book = bookRepository.findById(itemRequest.bookId())
                    .orElseThrow(() -> new NotFoundException(
                            "Book not found with id: " + itemRequest.bookId()));

            if (book.getAvailableQuantity() <= 0) {
                throw new ValidationException(
                        "Book '" + book.getTitle() + "' is out of stock");
            }

            if (itemRequest.quantity() > book.getAvailableQuantity()) {
                throw new ValidationException(
                        "Requested quantity exceeds available stock for book: "
                                + book.getTitle());
            }

            totalAmount += book.getPrice() * itemRequest.quantity();
        }

        Order order = Order.builder()
                .createdBy(user)
                .createdAt(ZonedDateTime.now(ZoneId.of("Z")))
                .status(OrderStatusEnum.PENDING)
                .totalAmount(totalAmount)
                .build();

        Order savedOrder = orderRepository.save(order);
        List<OrderItemDTO.Response> orderItemsList = new ArrayList<>();
        for (OrderItemDTO.Request itemRequest : orderRequest.orderItems()) {
            orderItemsList.add(orderItemService.createOrderItem(itemRequest, savedOrder));
        }

        return new OrderDTO.Response(
                savedOrder.getId(),
                savedOrder.getCreatedBy().getId(),
                savedOrder.getTotalAmount(),
                orderItemsList,
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