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
public class News {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime createAt;
}
