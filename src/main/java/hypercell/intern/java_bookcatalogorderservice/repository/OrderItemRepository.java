package hypercell.intern.java_bookcatalogorderservice.repository;

import hypercell.intern.java_bookcatalogorderservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
