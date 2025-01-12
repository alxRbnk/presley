package org.rbnk.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private String username;
    private String text;
    private Long newsId;
}
