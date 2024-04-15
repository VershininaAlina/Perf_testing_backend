package ru.alina.hr_backend_server.entity.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.answer.user.UserAnswer;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.entity.video.Video;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTestPass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne
    private Test test;

    @ManyToMany
    private List<UserAnswer> userAnswers;


    @Enumerated(EnumType.STRING)
    private UserTestStatus userTestStatus;

    @ManyToOne
    private Video video;

    private Date timeStart;
    private Date timeEnd;

    public UserTestPass(User user, Test test) {
        this.user = user;
        this.test = test;
        this.timeStart = new Date();
        this.timeEnd = null;
        this.userTestStatus = UserTestStatus.PENDING;
    }


    @Transient
    public UserAnswer addUserAnswer(UserAnswer userAnswer) {
        UserAnswer result = null;
        for (UserAnswer userAnswerItem : userAnswers) {
            if (userAnswerItem.getQuestion().getId() == userAnswer.getQuestion().getId()) {
                result = userAnswerItem;
                userAnswers.remove(userAnswerItem);
                break;
            }
        }
        userAnswers.add(userAnswer);
        return result;
    }
}
