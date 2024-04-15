package ru.alina.hr_backend_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alina.hr_backend_server.entity.response.ResponseVacancy;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;

import java.util.List;

@Repository
public interface ResponseVacancyRepository extends JpaRepository<ru.alina.hr_backend_server.entity.response.ResponseVacancy, Long> {
    ResponseVacancy getResponseVacanciesByUserAndVacancy(User user, Vacancy vacancy);
    List<ResponseVacancy> findAllByVacancy(Vacancy vacancy);
    @Query("SELECT r.vacancy FROM ResponseVacancy r WHERE r.user = :user")
    List<Vacancy> respondVacanciesByUser(@Param("user") User user);
}
