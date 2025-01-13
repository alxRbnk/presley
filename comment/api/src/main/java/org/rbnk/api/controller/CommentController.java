package org.rbnk.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rbnk.api.dto.CommentDto;
import org.rbnk.api.mapper.CommentMapper;
import org.rbnk.core.domain.Comment;
import org.rbnk.core.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    public CommentDto getById(@PathVariable("id") Long id) {
        return commentMapper.domainToDto(commentService.getById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    public List<CommentDto> getAll() {
        return commentService.getAll().stream()
                .map(commentMapper::domainToDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUBSCRIBER')")
    public void createComment(@RequestBody @Valid CommentDto dto) {
        Comment news = commentMapper.dtoToDomain(dto);
        commentService.create(news);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUBSCRIBER')")
    public void updateComment(@RequestBody @Valid CommentDto dto) {
        Comment comment = commentMapper.dtoToDomain(dto);
        commentService.update(comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('SUBSCRIBER')")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
    }

    @GetMapping("/news/paged")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    public Page<CommentDto> getCommentsByNewsId(@RequestParam("newsId") Long newsId,
                                                Pageable pageable) {
        return commentService.getCommentsByNewsId(newsId, pageable)
                .map(commentMapper::domainToDto);
    }

    @GetMapping("/news/comments")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "permitAll()")
    public List<CommentDto> getCommentsByNewsId(@RequestParam("newsId") Long newsId) {
        return commentService.getCommentsByNewsId(newsId).stream()
                .map(commentMapper::domainToDto)
                .toList();
    }

}
