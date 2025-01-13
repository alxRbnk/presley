package org.rbnk.api.client;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.rbnk.api.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "comment-service", url = "http://comment-service:8082")
public interface CommentClient {

    @GetMapping("/comments/comments")
    List<CommentDto> fetchCommentsByIds();

    @GetMapping("/comments/news/paged")
    Page<CommentDto> getCommentsByNewsId(@RequestParam("newsId") @Positive Long newsId,
                                         @RequestParam("page") @Min(0) int page,
                                         @RequestParam("size") @Min(1) int size);

    @GetMapping("/comments/news/comments")
    List<CommentDto> getCommentByNewsId(@RequestParam("newsId") @Positive Long newsId);
}
