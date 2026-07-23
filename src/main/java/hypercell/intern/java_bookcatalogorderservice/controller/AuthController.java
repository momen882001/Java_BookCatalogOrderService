package hypercell.intern.java_bookcatalogorderservice.controller;

import hypercell.intern.java_bookcatalogorderservice.dto.AuthDTO;
import hypercell.intern.java_bookcatalogorderservice.dto.UserDTO;
import hypercell.intern.java_bookcatalogorderservice.service.AuthService;
import hypercell.intern.java_bookcatalogorderservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO.Response> register(@Valid @RequestBody UserDTO.Request userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.Response> login(@Valid @RequestBody AuthDTO.Request loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(loginRequest));
    }

}
