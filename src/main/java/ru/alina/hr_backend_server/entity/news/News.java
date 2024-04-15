package ru.alina.hr_backend_server.entity.news;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150)
    private String header;
    @Column(length = 512)
    private String description;
    @Column(length = 4096)
    private String body;
    private Date date;

    public News(String header, String description, String body) {
        this.header = header;
        this.description = description;
        this.body = body;
        this.date = new Date();


    }

}