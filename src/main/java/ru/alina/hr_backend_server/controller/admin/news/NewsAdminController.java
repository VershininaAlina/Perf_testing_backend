package ru.alina.hr_backend_server.controller.admin.news;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alina.hr_backend_server.dto.news.NewsDto;
import ru.alina.hr_backend_server.entity.news.News;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.repository.NewsRepository;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Validated
@RestController
@RequestMapping("/admin/news")
@RequiredArgsConstructor
public class NewsAdminController {
    private final NewsAdminService newsAdminService;

    /**
     * Создание новости
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание новости. Позволяет создать новость")
   // @Secured("ROLE_ADMIN")
    public News createNews(@Valid @RequestBody NewsDto newsDto) {
        return newsAdminService.createNews(newsDto);
    }

    /**
     * Редактирование новости по ID
     */
    @PutMapping("/{id}")
    @Operation(description = "Редактирование новости по ID. Позволяет отредактировать новость")
    //@Secured("ROLE_ADMIN")
    public News editNews(@Valid @RequestBody NewsDto newsDto, @PathVariable Long id) {
        return newsAdminService.editNews(newsDto, id);
    }

    /**
     * Удаление новости по ID
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удаление новости по ID. Позволяет удалить новость")
    //@Secured("ROLE_ADMIN")
    public void deleteNews(@Valid @PathVariable Long id) {
        newsAdminService.deleteNews(id);
    }
}