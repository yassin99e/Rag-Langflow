package ma.ensa.backendspringboot.controller;


import ma.ensa.backendspringboot.dto.*;
import ma.ensa.backendspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }
}
