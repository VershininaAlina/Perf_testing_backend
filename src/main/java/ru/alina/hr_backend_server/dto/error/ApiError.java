package ru.alina.hr_backend_server.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "API Error")
public class ApiError {
    @Schema(description = "Error message")
    private String message;

    @Schema(description = "Error code")
    private Integer code;
}