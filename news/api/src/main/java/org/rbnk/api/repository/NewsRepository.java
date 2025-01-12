package org.rbnk.api.repository;


import org.rbnk.api.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    Page<NewsEntity> findByTitleContainingIgnoreCaseOrTextContainingIgnoreCase(String title, String text, Pageable pageable);
}
