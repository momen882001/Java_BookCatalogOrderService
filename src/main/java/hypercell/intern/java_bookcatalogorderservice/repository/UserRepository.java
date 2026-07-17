package hypercell.intern.java_bookcatalogorderservice.repository;

import hypercell.intern.java_bookcatalogorderservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
                SELECT u
                FROM User u
                LEFT JOIN FETCH u.books
                WHERE u.id = :id
            """)
    Optional<User> findUserWithBooks(Long id);
    
}
