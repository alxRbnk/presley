package org.rbnk.core.service;

import lombok.RequiredArgsConstructor;
import org.rbnk.core.adapter.Adapter;
import org.rbnk.core.domain.Comment;
import org.rbnk.core.exception.CommentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    public final Adapter adapter;

    public Comment getById(Long id) {
        return adapter.findById(id);
    }

    public List<Comment> getAll() {
        return adapter.findAll();
    }

    public void create(Comment comment) {
        adapter.save(comment);
    }

    public void update(Comment comment) {
        adapter.findById(comment.getId());
        comment.setCreateAt(LocalDateTime.now());
        adapter.update(comment);
    }

    public void delete(Long id) {
        adapter.findById(id);
        adapter.deleteById(id);
    }

    public Page<Comment> getCommentsByNewsId(Long newsId, Pageable pageable) {
        return adapter.findByNewsId(newsId, pageable);
    }

    public List<Comment> getCommentsByNewsId(Long newsId) {
        return adapter.findAllByNewsId(newsId);
    }

}
