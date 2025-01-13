package org.rbnk.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.rbnk.auth.model.Role;


@Getter
@Setter
@Builder
@ToString
public class UserDto {
    @NotEmpty(message = "Login cannot be empty")
    @Size(min = 2, max = 100, message = "Login must be between 4 and 100 characters")
    private String login;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 characters")
    private String password;

    @NotEmpty(message = "Username name cannot be empty")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email
    private String email;

}
