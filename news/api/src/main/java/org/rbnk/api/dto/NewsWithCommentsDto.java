package org.rbnk.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewsWithCommentsDto {
    private NewsDto newsDto;
    private Page<CommentDto> comments;
}
