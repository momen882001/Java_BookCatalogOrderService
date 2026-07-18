package hypercell.intern.java_bookcatalogorderservice.service;

import hypercell.intern.java_bookcatalogorderservice.dto.BookDto;
import hypercell.intern.java_bookcatalogorderservice.dto.UserDTO;
import hypercell.intern.java_bookcatalogorderservice.exception.NotFoundException;
import hypercell.intern.java_bookcatalogorderservice.model.User;
import hypercell.intern.java_bookcatalogorderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO.Response createUSer(UserDTO.Request request) {

        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .createdAt(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        userRepository.save(user);
        return new UserDTO.Response(user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getCreatedAt(),
                null
        );
    }

    public UserDTO.Response getUserById(Long id) {
        Optional<User> user = userRepository.findUserWithBooks(id);

        List<BookDto.Response> books = user.map(u -> u.getBooks().stream()
                .map(b -> new BookDto.Response(
                        b.getId(),
                        b.getTitle(),
                        b.getIsbn(),
                        b.getAuthor(),
                        b.getPrice(),
                        b.getAvailableQuantity(),
                        b.getCreatedAt(),
                        b.getUpdatedAt(),
                        null
                )).toList()).orElse(null);

        return user.map(u -> new UserDTO.Response(
                u.getId(),
                u.getFirstname(),
                u.getLastname(),
                u.getCreatedAt(),
                books
        )).orElse(null);
    }

    public void deleteUSer(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Employee with id " + id + " not found");
        }
    }

    public List<UserDTO.Response> getAllUsers() {
        return userRepository.findAllWithBooks().stream()
                .map(e -> new UserDTO.Response(e.getId(), e.getFirstname(), e.getLastname(), e.getCreatedAt(), e.getBooks().stream()
                        .map(b -> new BookDto.Response(
                                b.getId(),
                                b.getTitle(),
                                b.getIsbn(),
                                b.getAuthor(),
                                b.getPrice(),
                                b.getAvailableQuantity(),
                                b.getCreatedAt(),
                                b.getUpdatedAt(),
                                null
                        )).toList()))
                .toList();
    }


}
