package org.rbnk.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDto {

    private Long id;

    @NotNull(message = "username cannot be null")
    private String username;

    @NotNull(message = "text cannot be null")
    private String text;

    @NotNull(message = "newsId cannot be null")
    private Long newsId;
}
