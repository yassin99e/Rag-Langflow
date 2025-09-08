package ma.ensa.backendspringboot.service;

import ma.ensa.backendspringboot.dto.*;

public interface UserService {
    UserResponse register(UserRegisterRequest request);
    JwtResponse login(UserLoginRequest request);
}