package ru.alina.hr_backend_server.controller.video;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import ru.alina.hr_backend_server.dto.video.VideoMessage;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.entity.video.Video;
import ru.alina.hr_backend_server.exception.AuthenticationException;
import ru.alina.hr_backend_server.repository.UserRepository;
import ru.alina.hr_backend_server.repository.UserTestPassedRepository;
import ru.alina.hr_backend_server.repository.VideoRepository;
import ru.alina.hr_backend_server.service.JwtService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final static String VIDEO_FOLDER = "res/videos/";

    private final UserTestPassedRepository userTestPassedRepository;
    private final VideoRepository videoRepository;

    private HashMap<Long, FileOutputStream> fileOutputStreamHashMap = new HashMap<>();


    private Path videoPath;


    public void handleVideos(byte[] data, SimpMessageHeaderAccessor headerAccessor) {


        var user = (User) headerAccessor.getSessionAttributes().get("user");


        var userTestPassing = userTestPassedRepository.getUserTestPassByUserAndTimeEndIsNull(user);
        if (userTestPassing.size() > 1 || userTestPassing.size() == 0) {
            return;
        }

        var video = userTestPassing.get(0).getVideo();
        if (video == null) {
            var fileName = String.valueOf(VIDEO_FOLDER + System.currentTimeMillis()) + "_" + userTestPassing.get(0).getId() + ".webm";
            video = new Video(fileName, user);

            videoRepository.save(video);
            userTestPassing.get(0).setVideo(video);
            userTestPassedRepository.save(userTestPassing.get(0));
        }

        try {
            FileOutputStream fileOutputStream = fileOutputStream(video);
            write(fileOutputStream, new VideoMessage(data));
        } catch (FileNotFoundException e) {
            // TODO: What can be happens?
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO: What can be happens? idk
            throw new RuntimeException(e);
        }


    }


    public void stopVideoThread(User user) {

        var id = user.getId();
        FileOutputStream fileOutputStream = fileOutputStreamHashMap.get(id);
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileOutputStreamHashMap.remove(id);
        }
    }

    private void write(FileOutputStream fileOutputStream, VideoMessage videoMessage) throws IOException {
        fileOutputStream.write(videoMessage.getData());
    }

    private FileOutputStream fileOutputStream(Video video) throws FileNotFoundException {
        FileOutputStream fileOutputStream = fileOutputStreamHashMap.get(video.getUser().getId());
        if (fileOutputStream == null) {
            fileOutputStream = new FileOutputStream(video.getFileName());
            fileOutputStreamHashMap.put(video.getUser().getId(), fileOutputStream);
        }
        return fileOutputStream;
    }

    public void handleVideosBase64(String data, SimpMessageHeaderAccessor headerAccessor) {

        byte[] decodedBytes = Base64.getDecoder().decode(data);
        handleVideos(decodedBytes, headerAccessor);

    }
}
