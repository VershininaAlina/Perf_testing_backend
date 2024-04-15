package ru.alina.hr_backend_server.controller.news;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.entity.news.News;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.repository.NewsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsService {
    private static final Integer PAGE_SIZE = 30;

    private final NewsRepository newsRepository;

    public List<News> getNews(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        List<News> news = newsRepository.findAll(pageable).stream().toList();
        return news;
    }

    public News getNew(Long id) {
        var news = newsRepository.getNewsById(id);
        if (news == null) {
            throw new NotFoundException("News not found");
        }
        return news;
    }

    public Long getCountNews() {
        return newsRepository.count();
    }

    public List<News> findNews(String query, Integer page) {
        return newsRepository.findNewsByQuery(query, PageRequest.of(page, PAGE_SIZE));
    }

    public Long findNews(String query) {
        return newsRepository.countFindNews(query);
    }
}