package org.rbnk.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class NewsDto {

    private Long id;

    @NotNull(message = "title cannot be null")
    @Size(min = 2, max = 50, message = "title should be between 2 and 255 characters")
    private String title;

    @NotNull(message = "text cannot be null")
    @Size(min = 2, message = "text min 2 char")
    private String text;
}
