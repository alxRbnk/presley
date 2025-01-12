package org.rbnk.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rbnk.core.adapter.Adapter;
import org.rbnk.core.domain.Comment;
import org.rbnk.core.exception.NewsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private Adapter adapter;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testGetCommentsForNews_ShouldReturnPagedComments() {
        Long newsId = 1L;
        Page<Comment> page = new PageImpl<>(List.of(Comment.builder().id(1L).newsId(1L).text("Comment1").build()));
        when(adapter.getCommentsForNews(newsId, 0, 10)).thenReturn(page);

        Page<Comment> result = commentService.getCommentsForNews(newsId, 0, 10);

        assertThat(result).isEqualTo(page);
    }

    @Test
    void testGetCommentForNews_ShouldReturnComment() {
        Long newsId = 1L;
        List<Comment> comments = List.of(Comment.builder().id(1L).newsId(1L).text("Comment1").build(),
                Comment.builder().id(2L).newsId(2L).text("Comment2").build());
        when(adapter.getCommentsByNewsId(newsId)).thenReturn(comments);

        Comment result = commentService.getCommentForNews(newsId, 2);

        assertThat(result).isEqualTo(comments.get(1));
    }

    @Test
    void testGetCommentForNews_ShouldThrowExceptionForInvalidIndex() {
        Long newsId = 1L;
        List<Comment> comments = List.of( Comment.builder().id(1L).newsId(1L).text("Comment1").build());
        when(adapter.getCommentsByNewsId(newsId)).thenReturn(comments);

        assertThatThrownBy(() -> commentService.getCommentForNews(newsId, 2))
                .isInstanceOf(NewsException.class)
                .hasMessage("Incorrect comment index");
    }
}