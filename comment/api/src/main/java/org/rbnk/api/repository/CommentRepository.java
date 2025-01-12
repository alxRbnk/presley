package org.rbnk.api.repository;

import org.rbnk.api.entity.CommentEntity;
import org.rbnk.core.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findByNewsId(Long newsId, Pageable pageable);

    List<CommentEntity> findAllByNewsId(Long newsId);

}
