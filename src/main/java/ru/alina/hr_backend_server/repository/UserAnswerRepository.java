package ru.alina.hr_backend_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alina.hr_backend_server.entity.answer.user.UserAnswer;
import ru.alina.hr_backend_server.entity.question.Question;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    UserAnswer getUserAnswerByQuestion(Question question);
}
