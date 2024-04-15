package ru.alina.hr_backend_server.controller.admin.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.dto.news.NewsDto;
import ru.alina.hr_backend_server.entity.news.News;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.repository.NewsRepository;

@Service
@RequiredArgsConstructor
public class NewsAdminService {
    private final NewsRepository newsRepository;


    public News createNews(NewsDto newsDto) {
        var news = new News(newsDto.getHeader(), newsDto.getDescription(), newsDto.getBody());
        newsRepository.save(news);
        return news;
    }

    public News editNews(NewsDto newsDto, Long id) {
        var news = newsRepository.getNewsById(id);

        if (news == null) {
            throw new NotFoundException("News not found");
        }


        news.setBody(newsDto.getBody());
        news.setHeader(news.getHeader());
        news.setDescription(news.getDescription());
        newsRepository.save(news);
        return news;
    }


    public void deleteNews(Long id) {
        var news = newsRepository.getNewsById(id);
        if (news == null) {
            throw new NotFoundException("News not found");
        }
        newsRepository.delete(news);
    }
}
