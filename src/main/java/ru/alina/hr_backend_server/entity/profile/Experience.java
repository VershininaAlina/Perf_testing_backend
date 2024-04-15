package ru.alina.hr_backend_server.entity.profile;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.user.User;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String position;
    private Double yearOfExpirience;
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Experience(String position, Double yearOfExpirience, String description, User user) {
        this.position = position;
        this.yearOfExpirience = yearOfExpirience;
        this.description = description;
        this.user = user;
    }
}