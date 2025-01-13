package org.rbnk.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rbnk.core.adapter.Adapter;
import org.rbnk.core.cache.CacheService;
import org.rbnk.core.domain.News;
import org.rbnk.core.exception.NewsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {
    private final Adapter adapter;
    private final CacheService<News> cacheService;

    public News getById(Long id) {
        if (id <= 0) {
            throw new NewsException("id must be greater than zero");
        }
        log.info("Fetching news by id: {}", id);
        News cachedNews = cacheService.get(id);
        if (cachedNews != null) {
            log.info("Returning cached news with id: {}", id);
            return cachedNews;
        }
        News news = adapter.findById(id);
        cacheService.put(id, news);
        log.info("Fetched news from database and cached it with id: {}", id);
        return news;
    }

    public List<News> getAll() {
        log.info("Fetching all news");
        return adapter.findAll();
    }

    public void create(News news) {
        log.info("Creating news: {}", news);
        News savedNews = adapter.save(news);
        cacheService.put(savedNews.getId(), savedNews);
        log.info("Created and cached news with id: {}", savedNews.getId());
    }

    public void update(News news) {

        log.info("Updating news: {}", news);
        news.setCreateAt(LocalDateTime.now());
        News updatedNews = adapter.update(news);
        cacheService.update(updatedNews.getId(), updatedNews);
        log.info("Updated and cached news with id: {}", updatedNews.getId());
    }

    public void delete(Long id) {
        if (id <= 0) {
            throw new NewsException("id must be greater than zero");
        }
        adapter.findById(id);
        log.info("Deleting news with id: {}", id);
        adapter.deleteById(id);
        cacheService.remove(id);
        log.info("Deleted news with id: {}", id);
    }

    public Page<News> getAllNews(int page, int size) {
        log.info("Fetching news with pagination (Page: {}, Size: {})", page, size);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return adapter.getAllNews(pageable);
    }

    public Page<News> searchNews(String keyword, int page, int size) {
        log.info("Searching news with keyword: {} (Page: {}, Size: {})", keyword, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return adapter.searchNews(keyword, pageable);
    }
}
