package ru.alina.hr_backend_server.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User Authentication DTO")
public class UserAuthDto {
    @Schema(description = "Authentication token")
    private String token;
}