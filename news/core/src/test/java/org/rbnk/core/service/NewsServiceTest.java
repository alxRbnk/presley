package org.rbnk.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rbnk.core.adapter.Adapter;
import org.rbnk.core.cache.CacheService;
import org.rbnk.core.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private Adapter adapter;

    @Mock
    private CacheService<News> cacheService;

    @InjectMocks
    private NewsService newsService;

    @Test
    void testGetById_WhenCached_ShouldReturnCachedNews() {
        Long newsId = 1L;
        News cachedNews = new News(newsId, "Cached Title", "Cached Content", LocalDateTime.now());
        when(cacheService.get(newsId)).thenReturn(cachedNews);

        News result = newsService.getById(newsId);

        assertThat(result).isEqualTo(cachedNews);
        verify(adapter, never()).findById(newsId);
    }

    @Test
    void testGetById_WhenNotCached_ShouldFetchAndCacheNews() {
        Long newsId = 1L;
        News fetchedNews = new News(newsId, "Fetched Title", "Fetched Content", LocalDateTime.now());
        when(cacheService.get(newsId)).thenReturn(null);
        when(adapter.findById(newsId)).thenReturn(fetchedNews);

        News result = newsService.getById(newsId);

        assertThat(result).isEqualTo(fetchedNews);
        verify(cacheService).put(newsId, fetchedNews);
    }

    @Test
    void testGetAll_ShouldReturnAllNews() {
        List<News> newsList = Arrays.asList(
                new News(1L, "Title1", "Content1", LocalDateTime.now()),
                new News(2L, "Title2", "Content2", LocalDateTime.now())
        );
        when(adapter.findAll()).thenReturn(newsList);

        List<News> result = newsService.getAll();

        assertThat(result).isEqualTo(newsList);
    }

    @Test
    void testCreate_ShouldSaveAndCacheNews() {
        News newNews = new News(null, "New Title", "New Content", LocalDateTime.now());
        News savedNews = new News(1L, "New Title", "New Content", LocalDateTime.now());
        when(adapter.save(newNews)).thenReturn(savedNews);

        newsService.create(newNews);

        verify(cacheService).put(savedNews.getId(), savedNews);
    }

    @Test
    void testUpdate_ShouldUpdateNewsAndCache() {
        News news = new News(1L, "Updated Title", "Updated Content", LocalDateTime.now());
        when(adapter.update(news)).thenReturn(news);

        newsService.update(news);

        verify(cacheService).update(news.getId(), news);
    }

    @Test
    void testDelete_ShouldRemoveFromCacheAndAdapter() {
        Long newsId = 1L;

        newsService.delete(newsId);

        verify(adapter).deleteById(newsId);
        verify(cacheService).remove(newsId);
    }

    @Test
    void testGetAllNews_ShouldReturnPagedNews() {
        Page<News> page = new PageImpl<>(List.of(new News(1L, "Title", "Content", LocalDateTime.now())));
        Pageable pageable = PageRequest.of(0, 10);
        when(adapter.getAllNews(pageable)).thenReturn(page);

        Page<News> result = newsService.getAllNews(0, 10);

        assertThat(result).isEqualTo(page);
    }

    @Test
    void testSearchNews_ShouldReturnSearchResults() {
        String keyword = "search";
        Page<News> page = new PageImpl<>(List.of(new News(1L, "Title", "Content", LocalDateTime.now())));
        Pageable pageable = PageRequest.of(0, 10);
        when(adapter.searchNews(keyword, pageable)).thenReturn(page);

        Page<News> result = newsService.searchNews(keyword, 0, 10);

        assertThat(result).isEqualTo(page);
    }

}
