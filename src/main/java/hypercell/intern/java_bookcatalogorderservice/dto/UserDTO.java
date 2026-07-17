package hypercell.intern.java_bookcatalogorderservice.dto;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class UserDTO {
    public record Request(String firstname, String lastname) {
    }

    public record Response(Long id, String firstname, String lastname, ZonedDateTime createdAt,
                           List<BookDto.Response> books) {
    }
}
