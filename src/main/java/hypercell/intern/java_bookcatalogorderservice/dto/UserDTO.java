package hypercell.intern.java_bookcatalogorderservice.dto;

public abstract class UserDTO {
    public record Request(String firstname, String lastname) {
    }

    public record Response(Long id, String firstname, String lastname) {
    }
}
