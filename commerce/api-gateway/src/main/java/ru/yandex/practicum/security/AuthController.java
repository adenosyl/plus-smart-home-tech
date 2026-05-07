package ru.yandex.practicum.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/token")
    public Map<String, String> token(
            @RequestParam String username
    ) {

        String token =
                jwtService.generateToken(username);

        return Map.of(
                "token",
                token
        );
    }
}