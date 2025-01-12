package org.rbnk.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.rbnk.auth.model.Role;

@Getter
@Setter
@Builder
@ToString
public class ChangeRoleDto {
    @NotNull(message = "Id not be empty")
    private Long id;

    @NotNull(message = "Role not be empty")
    private Role role;
}
