package ru.alina.hr_backend_server.entity.answer.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.answer.question.Answer;
import ru.alina.hr_backend_server.entity.question.Question;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;

    @ManyToMany
    private List<Answer> answers;

    public UserAnswer(Question question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }
}
