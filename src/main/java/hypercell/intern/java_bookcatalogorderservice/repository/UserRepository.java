package hypercell.intern.java_bookcatalogorderservice.repository;

import hypercell.intern.java_bookcatalogorderservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
