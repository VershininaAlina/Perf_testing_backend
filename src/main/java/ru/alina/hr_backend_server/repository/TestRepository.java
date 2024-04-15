package ru.alina.hr_backend_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alina.hr_backend_server.entity.test.Test;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test getTestById(Long id);
    List<Test> findAllByVacancy(Vacancy vacancy);

}
