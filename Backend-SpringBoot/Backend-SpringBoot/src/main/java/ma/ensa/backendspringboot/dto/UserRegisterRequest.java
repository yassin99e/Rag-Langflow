package ma.ensa.backendspringboot.dto;


import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String email;
    private String password;
}