package ru.alina.hr_backend_server.controller.news;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alina.hr_backend_server.dto.error.ApiError;
import ru.alina.hr_backend_server.entity.news.News;


import java.util.List;

@Validated
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Tag(description = "Get news and get news by id", name = "News controller")
public class NewsController {


    private final NewsService newsService;


    @Operation(summary = "Get a list of news", description = "Get a list of news with pagination", responses = {@ApiResponse(responseCode = "200", description = "The list of news", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = News.class)))),})
    @GetMapping
    public ResponseEntity<List<News>> getAllNews(@RequestParam(defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(newsService.getNews(pageNumber));
    }


    @Operation(summary = "Get a news by id", description = "Get a news by its id", responses = {@ApiResponse(responseCode = "200", description = "The news with the given id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = News.class))), @ApiResponse(responseCode = "404", description = "News not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<News> getNew(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNew(id));
    }


    @GetMapping("/search")
    public ResponseEntity<List<News>> findNews(@RequestParam("q") String query, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(newsService.findNews(query, page));

    }

    @GetMapping("/count-search")
    public ResponseEntity<Long> findNews(@RequestParam("q") String query) {
        return ResponseEntity.ok(newsService.findNews(query));

    }

    @GetMapping("/count")
    public ResponseEntity<Long> countNews() {
        return ResponseEntity.ok(newsService.getCountNews());
    }
    /*
    @Operation(summary = "Create a new news", description = "Create a new news", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class))
    ), responses = {
            @ApiResponse(responseCode = "201", description = "The news was created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = News.class))),
    })
    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody News news) {
        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }


    // PUT update an existing news

    @Operation(summary = "Update a news", description = "Update a news with the given id", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class))
    ), parameters = @Parameter(name = "id", description = "The id of the news to update", schema = @Schema(type = "Long")), responses = {
            @ApiResponse(responseCode = "200", description = "The news was updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = News.class))),
            @ApiResponse(responseCode = "404", description = "News not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News updatedNews) {
        return ResponseEntity.ok(updatedNews);
    }


    // DELETE a news

    @Operation(summary = "Delete a news", description = "Delete a news with the given id",

            responses = {

                    @ApiResponse(responseCode = "204", description = "The news was deleted"),

                    @ApiResponse(responseCode = "404", description = "News not found",

                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))

            })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        return ResponseEntity.noContent().build();

    }

     */


}
