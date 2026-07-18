package hypercell.intern.java_bookcatalogorderservice.mapper;

import hypercell.intern.java_bookcatalogorderservice.dto.UserDTO;
import hypercell.intern.java_bookcatalogorderservice.model.User;

public class UserMapper {
    public static UserDTO.Response toResponse(User userEntity) {
        return new UserDTO.Response(
                userEntity.getId(),
                userEntity.getFirstname(),
                userEntity.getLastname(),
                userEntity.getCreatedAt(),
                userEntity.getBooks().stream()
                        .map(BookMapper::toResponse)
                        .toList()
        );
    }
}
