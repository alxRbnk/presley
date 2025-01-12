package org.rbnk.api.client;

import org.rbnk.api.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "comment-service", url = "http://localhost:8082")
public interface CommentClient {

    @GetMapping("/comments/comments")
    List<CommentDto> fetchCommentsByIds();

    @GetMapping("/comments/news/comments")
    Page<CommentDto> getCommentsByNewsId(@RequestParam("newsId") Long newsId,
                                         @RequestParam("page") int page,
                                         @RequestParam("size") int size);

    @GetMapping("/comments/news/comments/comments")
    List<CommentDto> getCommentByNewsId(@RequestParam("newsId") Long newsId);
}
