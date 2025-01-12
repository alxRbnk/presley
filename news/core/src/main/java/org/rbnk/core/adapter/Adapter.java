package org.rbnk.core.adapter;

import org.rbnk.core.domain.Comment;
import org.rbnk.core.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Adapter {
    News findById(Long id);

    List<News> findAll();

    News save(News news);

    News update(News news);

    void deleteById(Long id);

    Page<News> getAllNews(Pageable pageable);

    Page<Comment> getCommentsForNews(Long newsId, int page, int size);

    List<Comment> getCommentsByNewsId(Long newsId);

    Page<News> searchNews(String keyword, Pageable pageable);
}
