package hypercell.intern.java_bookcatalogorderservice.repository;

import hypercell.intern.java_bookcatalogorderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
