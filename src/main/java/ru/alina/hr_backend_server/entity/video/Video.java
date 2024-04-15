package ru.alina.hr_backend_server.entity.video;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.user.User;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String fileName;
    private Date timeStart;
    private Date timeEnd;
    private Boolean isRecording;

    public Video(String fileName, User user) {
        this.fileName = fileName;
        this.user = user;
        this.isRecording = true;
        this.timeStart = new Date();
    }
}
