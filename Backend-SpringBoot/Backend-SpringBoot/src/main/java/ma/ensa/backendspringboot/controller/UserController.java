package ma.ensa.backendspringboot.controller;

import lombok.RequiredArgsConstructor;
import ma.ensa.backendspringboot.dto.UserResponse;
import ma.ensa.backendspringboot.model.User;
import ma.ensa.backendspringboot.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;


    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName(); // email stored in JWT subject

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .id(user.getId())
                .build();

        return userResponse;
    }
}
