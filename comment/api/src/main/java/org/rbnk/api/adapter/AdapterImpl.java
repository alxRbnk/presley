package org.rbnk.api.adapter;

import lombok.RequiredArgsConstructor;
import org.rbnk.api.entity.CommentEntity;
import org.rbnk.api.mapper.CommentMapper;
import org.rbnk.api.repository.CommentRepository;
import org.rbnk.core.adapter.Adapter;
import org.rbnk.core.domain.Comment;
import org.rbnk.core.exception.CommentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdapterImpl implements Adapter {
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;

    @Override
    public Comment findById(Long id) {
        CommentEntity entity = commentRepository.findById(id).orElseThrow(()
                -> new CommentException("incorrect"));
        return mapper.entityToDomain(entity);
    }

    @Override
    public List<Comment> findAll() {
        List<CommentEntity> entities = commentRepository.findAll();
        return entities.stream()
                .map(mapper::entityToDomain)
                .toList();
    }

    @Override
    public void save(Comment comment) {
        CommentEntity newsEntity = mapper.domainToEntity(comment);
        commentRepository.save(newsEntity);
    }

    @Override
    public void update(Comment comment) {
        CommentEntity newsEntity = mapper.domainToEntity(comment);
        newsEntity.setId(comment.getId());
        commentRepository.save(newsEntity);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> findByNewsId(Long newsId, Pageable pageable) {
        return commentRepository.findByNewsId(newsId, pageable)
                .map(mapper::entityToDomain);
    }

    @Override
    public List<Comment> findAllByNewsId(Long newsId){
       return commentRepository.findAllByNewsId(newsId).stream()
               .map(mapper::entityToDomain)
               .toList();
    }
}
