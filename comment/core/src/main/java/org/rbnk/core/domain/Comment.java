package org.rbnk.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private LocalDateTime createAt;
    private String text;
    private String username;
    private Long newsId;
}
