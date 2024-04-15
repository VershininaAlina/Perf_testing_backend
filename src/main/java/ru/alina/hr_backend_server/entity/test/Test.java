package ru.alina.hr_backend_server.entity.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.question.Question;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vacancy vacancy;

    @Transient
    public Question getQuestionById(Long id) {
        for (Question question : questions) {
            if (question.getId() == id) {
                return question;
            }
        }
        return null;
    }
}
