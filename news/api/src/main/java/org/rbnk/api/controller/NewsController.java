package org.rbnk.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rbnk.api.dto.CommentDto;
import org.rbnk.api.dto.NewsDto;
import org.rbnk.api.dto.NewsWithCommentsDto;
import org.rbnk.api.mapper.CommentMapper;
import org.rbnk.api.mapper.NewsMapper;
import org.rbnk.core.service.CommentService;
import org.rbnk.core.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
@Tag(name = "News", description = "Operations related to news articles")
public class NewsController {
    public final NewsService newsService;
    private final CommentService commentService;
    public final NewsMapper newsMapper;
    public final CommentMapper commentMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    @Operation(summary = "Delete a news article")
    public NewsDto getById(@PathVariable("id") Long id) {
        return newsMapper.domainToDto(newsService.getById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    @Operation(summary = "Get all news")
    public List<NewsDto> getAll() {
        return newsService.getAll().stream()
                .map(newsMapper::domainToDto)
                .toList();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('JOURNALIST')")
    @Operation(summary = "Create a news article")
    public void createNews(@RequestBody @Valid NewsDto dto) {
        newsService.create(newsMapper.dtoToDomain(dto));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('JOURNALIST')")
    @Operation(summary = "Update a news article")
    public void updateNews(@RequestBody @Valid NewsDto dto) {
        newsService.update(newsMapper.dtoToDomain(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('JOURNALIST')")
    @Operation(summary = "Delete a news article")
    public void deleteNews(@PathVariable("id") Long id) {
        newsService.delete(id);
    }

    @GetMapping("/paged")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    @Operation(summary = "Get news with pagination")
    public Page<NewsDto> getPagedNews(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return newsService.getAllNews(page, size).map(newsMapper::domainToDto);
    }

    @GetMapping("/{newsId}/comments")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    @Operation(summary = "Get news with comments")
    public NewsWithCommentsDto getNewsWithComments(@PathVariable("newsId") Long newsId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Page<CommentDto> comments = commentService.getCommentsForNews(newsId, page, size)
                .map(commentMapper::domainToDto);
        NewsDto newsDto = newsMapper.domainToDto(newsService.getById(newsId));
        return NewsWithCommentsDto.builder().comments(comments).newsDto(newsDto).build();
    }

    @GetMapping("/{newsId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    @Operation(summary = "Get a comment by news and comment ID")
    public CommentDto getCommentByNewsAndIndex(@PathVariable("newsId") Long newsId,
                                               @PathVariable("commentId") Integer commentId) {
        return commentMapper.domainToDto(commentService.getCommentForNews(newsId, commentId));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    @Operation(summary = "Search news by keyword")
    public Page<NewsDto> searchNews(@RequestParam("keyword") String keyword,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return newsService.searchNews(keyword, page, size).map(newsMapper::domainToDto);
    }
}
