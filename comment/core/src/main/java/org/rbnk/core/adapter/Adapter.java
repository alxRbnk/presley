package org.rbnk.core.adapter;

import org.rbnk.core.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Adapter {

    Comment findById(Long id);

    List<Comment> findAll();

    void save(Comment comment);

    void update(Comment comment);

    void deleteById(Long id);

    Page<Comment> findByNewsId(Long newsId, Pageable pageable);

    List<Comment> findAllByNewsId(Long newsId);
}
