package ru.alina.hr_backend_server.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "User Authentication DTO")
public class UserAuthenticationDto {
    @Schema(description = "User email")
    private String email;

    @Schema(description = "User password")
    private String password;
}