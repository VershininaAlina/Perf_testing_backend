package ru.alina.hr_backend_server.controller.video;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;
import ru.alina.hr_backend_server.dto.video.VideoMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
public class VideoController {

    private final VideoService videoService;


    @MessageMapping("/video_thread")
    public void handleVideoPut(String data, SimpMessageHeaderAccessor headerAccessor) {
        videoService.handleVideosBase64(data, headerAccessor);
    }


    @GetMapping("/video")
    public ResponseEntity<Resource> getVideo(Authentication authentication, @RequestParam String name) throws FileNotFoundException {
        System.out.println(name);
        File file = new File("res/videos/" + name);
        Path filePath = file.toPath();

        Resource resource = new InputStreamResource(new FileInputStream(filePath.toFile()));

        return ResponseEntity.ok()
                .contentLength(filePath.toFile().length())
                .contentType(MediaType.parseMediaType("video/webm"))
                .body(resource);

    }
}