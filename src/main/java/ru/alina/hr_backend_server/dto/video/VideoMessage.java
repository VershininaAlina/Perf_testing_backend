package ru.alina.hr_backend_server.dto.video;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для видео-сообщения")
public class VideoMessage {

    @Schema(description = "Данные видео")
    private byte[] data;

}