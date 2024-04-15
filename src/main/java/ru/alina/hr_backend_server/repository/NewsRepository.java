package ru.alina.hr_backend_server.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alina.hr_backend_server.entity.news.News;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    News getNewsById(Long id);

    @Query("SELECT n FROM News n WHERE n.description LIKE %:q% OR n.body LIKE %:q% OR n.header LIKE %:q%")
    List<News> findNewsByQuery(@Param("q") String q, Pageable pageable);

    @Query("SELECT COUNT(n) FROM News n WHERE n.description LIKE %:q% OR n.body LIKE %:q% OR n.header LIKE %:q%")
    long countFindNews(@Param("q") String q);
}
