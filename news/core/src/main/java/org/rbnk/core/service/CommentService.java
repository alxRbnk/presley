package org.rbnk.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rbnk.core.adapter.Adapter;
import org.rbnk.core.domain.Comment;
import org.rbnk.core.exception.NewsException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final Adapter adapter;

    public Page<Comment> getCommentsForNews(Long newsId, int page, int size) {
        log.info("Fetching comments for newsId: {} (Page: {}, Size: {})", newsId, page, size);
        return adapter.getCommentsForNews(newsId, page, size);
    }

    public Comment getCommentForNews(Long newsId, Integer index) {
        log.debug("Fetching comment for newsId: {} at index: {}", newsId, index);
        List<Comment> commentsByNewsId = adapter.getCommentsByNewsId(newsId);
        if (commentsByNewsId.size() < index || index < 1) {
            log.error("Incorrect comment index: {}", index);
            throw new NewsException("Incorrect comment index");
        }
        index--;
        Comment comment = commentsByNewsId.get(index);
        log.info("Found comment: {}", comment);
        return comment;
    }

}
