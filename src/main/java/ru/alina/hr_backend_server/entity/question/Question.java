package ru.alina.hr_backend_server.entity.question;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.answer.question.Answer;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Answer> answers;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Answer> options;

    @Transient
    public Answer getAnswerById(Long id) {

        for (Answer answer : options) {
            if (answer.getId() == id) {
                return answer;
            }
        }

        return null;
    }

}
