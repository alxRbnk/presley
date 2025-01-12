package org.rbnk.api.adapter;

import lombok.RequiredArgsConstructor;
import org.rbnk.api.client.CommentClient;
import org.rbnk.api.dto.CommentDto;
import org.rbnk.api.entity.NewsEntity;
import org.rbnk.api.mapper.CommentMapper;
import org.rbnk.api.mapper.NewsMapper;
import org.rbnk.api.repository.NewsRepository;
import org.rbnk.core.adapter.Adapter;
import org.rbnk.core.domain.Comment;
import org.rbnk.core.domain.News;
import org.rbnk.core.exception.NewsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdapterImpl implements Adapter {
    private final NewsRepository newsRepository;
    private final CommentClient commentClient;
    private final NewsMapper newsMapper;
    private final CommentMapper commentMapper;

    @Override
    public News findById(Long id) {
        NewsEntity entity = newsRepository.findById(id).orElseThrow(() -> new NewsException("incorrect"));
        return newsMapper.entityToDomain(entity);
    }

    @Override
    public List<News> findAll() {
        List<NewsEntity> entities = newsRepository.findAll();
        return entities.stream()
                .map(newsMapper::entityToDomain)
                .toList();
    }

    @Override
    public News save(News news) {
        NewsEntity newsEntity = newsMapper.domainToEntity(news);
        return newsMapper.entityToDomain(newsRepository.save(newsEntity));
    }

    @Override
    public News update(News news) {
        NewsEntity newsEntity = newsMapper.domainToEntity(news);
        return newsMapper.entityToDomain(newsRepository.save(newsEntity));
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    public Page<News> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(newsMapper::entityToDomain);
    }

    public Page<Comment> getCommentsForNews(Long newsId, int page, int size) {
        Page<CommentDto> commentsByNewsId = commentClient.getCommentsByNewsId(newsId, page, size);
        return commentsByNewsId.map(commentMapper::dtoToDomain);
    }

    public List<Comment> getCommentsByNewsId(Long newsId) {
        List<CommentDto> comments = commentClient.getCommentByNewsId(newsId);
        return comments.stream().map(commentMapper::dtoToDomain).toList();
    }

    public Page<News> searchNews(String keyword, Pageable pageable) {
        return newsRepository
                .findByTitleContainingIgnoreCaseOrTextContainingIgnoreCase(keyword, keyword, pageable)
                .map(newsMapper::entityToDomain);
    }
}
