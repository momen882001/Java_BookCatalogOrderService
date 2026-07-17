package hypercell.intern.java_bookcatalogorderservice.service;

import hypercell.intern.java_bookcatalogorderservice.dto.UserDTO;
import hypercell.intern.java_bookcatalogorderservice.exception.NotFoundException;
import hypercell.intern.java_bookcatalogorderservice.model.User;
import hypercell.intern.java_bookcatalogorderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
                .build();
        userRepository.save(user);
        return new UserDTO.Response(user.getId(), user.getFirstname(), user.getLastname());
    }

    public UserDTO.Response getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user
                .map(e -> new UserDTO.Response(e.getId(), e.getFirstname(), e.getLastname()))
                .orElseThrow(() -> new NotFoundException("Employee with id " + id + " not found"));
    }

    public void deleteUSer(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Employee with id " + id + " not found");
        }
    }

    public List<UserDTO.Response> getAllUsers() {
        return userRepository.findAll().stream()
                .map(e -> new UserDTO.Response(e.getId(), e.getFirstname(), e.getLastname()))
                .toList();
    }
}
