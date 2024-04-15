package ru.alina.hr_backend_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    Vacancy getVacancyById(Long id);


    @Query("SELECT v FROM Vacancy v WHERE LOWER(v.description) LIKE LOWER(CONCAT('%', :keyWord, '%')) AND SIZE(v.responseVacancyUser) > :size")
    Page<Vacancy> vacancyGet(@Param("keyWord") String keyWord, @Param("size") int size, Pageable pageable);

    @Query("SELECT v FROM Vacancy v WHERE LOWER(v.description) LIKE LOWER(CONCAT('%', :keyWord, '%'))")
    Page<Vacancy> vacancyGet(@Param("keyWord") String keyWord, Pageable pageable);
}
