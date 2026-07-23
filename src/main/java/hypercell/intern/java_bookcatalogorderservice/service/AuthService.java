package hypercell.intern.java_bookcatalogorderservice.service;

import hypercell.intern.java_bookcatalogorderservice.dto.AuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import hypercell.intern.java_bookcatalogorderservice.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthDTO.Response authenticate(AuthDTO.Request request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        return new AuthDTO.Response(jwtService.generateToken(request.username()));
    }
}
