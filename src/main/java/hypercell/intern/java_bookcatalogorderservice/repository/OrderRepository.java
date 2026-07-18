package hypercell.intern.java_bookcatalogorderservice.repository;

import hypercell.intern.java_bookcatalogorderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
                SELECT DISTINCT o
                FROM Order o
                LEFT JOIN FETCH o.orderItems oi
                LEFT JOIN FETCH oi.book
                WHERE o.id = :id
            """)
    Optional<Order> findByIdWithOrderItems(Long id);

    @Query("""
            SELECT DISTINCT o
            FROM Order o
            LEFT JOIN FETCH o.orderItems oi
            LEFT JOIN FETCH oi.book
            """)
    List<Order> findAllWithOrderItems();
}
