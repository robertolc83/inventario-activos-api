package com.example.inventario_activos.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventario_activos.application.dto.request.LoginRequestDTO;
import com.example.inventario_activos.application.dto.response.AuthResponseDTO;
import com.example.inventario_activos.application.usecase.AuthUseCase;
import com.example.inventario_activos.domain.model.User;
import com.example.inventario_activos.infrastructure.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        User user = authUseCase.findByUsername(request.username())
                .orElse(null);

        // Validar si el usuario existe y si la contraseña coincide con el hash
        if (user == null || !passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        // Generar JWT firmado con el rol real del usuario desde la BD
        String token = jwtUtils.generateToken(user.getUsername(), user.getRole());

        return ResponseEntity.ok(new AuthResponseDTO(token, user.getUsername(), user.getRole()));
    }

}
