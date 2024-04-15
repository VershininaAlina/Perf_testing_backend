package ru.alina.hr_backend_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alina.hr_backend_server.entity.test.Test;
import ru.alina.hr_backend_server.entity.test.UserTestPass;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;

import java.util.List;

@Repository
public interface UserTestPassedRepository extends JpaRepository<UserTestPass, Long> {
    UserTestPass getUserTestPassByTestAndUser(Test test, User user);

    @Query("SELECT u FROM UserTestPass u WHERE u.test IN :tests")
    List<UserTestPass> findAllByTests(@Param("tests") List<Test> tests);

    @Query("SELECT u FROM UserTestPass u WHERE u.user = :user AND u.test.vacancy IN :vacancies AND u.userTestStatus = 'PASSED'")
    List<UserTestPass> getUserTestPassByVacanciesAndUser(@Param("user") User user, @Param("vacancies") List<Vacancy> vacancies);


    @Query("SELECT u FROM UserTestPass u WHERE u.user = :user AND u.test.vacancy = :vacancy AND u.userTestStatus = 'PASSED'")
    List<UserTestPass> getUserTestPassByVacancyAndUser(@Param("user") User user, @Param("vacancy") Vacancy vacancy);

    @Query("SELECT u FROM UserTestPass u WHERE  u.user=:user AND u.userTestStatus = 'PENDING'")
    List<UserTestPass> getUserTestPassByUserAndTimeEndIsNull(@Param("user") User user);

}
