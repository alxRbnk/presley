package org.rbnk.api.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "news cannot be null")
    private NewsDto newsDto;

    @NotNull(message = "comments cannot be null")
    private Page<CommentDto> comments;
}
