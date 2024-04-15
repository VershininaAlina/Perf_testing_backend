package ru.alina.hr_backend_server.entity.vacancy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.test.Test;
import ru.alina.hr_backend_server.entity.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 2048)
    private String description;

    @ManyToMany
    private List<User> responseVacancyUser = new ArrayList<>();

    @Column(length = 2048)
    private String responsibilities;
    @Column(length = 2048)
    private String requirements;
    @Column(length = 2048)
    private String willPlus;
    @Column(length = 2048)
    private String conditions;

    @ManyToMany
    private List<Test> tests = new ArrayList<>();

}
