package hypercell.intern.java_bookcatalogorderservice.controller;

import hypercell.intern.java_bookcatalogorderservice.dto.OrderDTO;
import hypercell.intern.java_bookcatalogorderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO.Response> createOrder(@Valid @RequestBody OrderDTO.Request orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequest));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderDTO.Response> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO.Response> getByIdWithOrderItems(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getByIdWithOrderItems(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO.Response>> getAllWithOrderItems() {
        return ResponseEntity.ok(orderService.getAllWithOrderItems());
    }
}
