package org.rbnk.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.rbnk.auth.model.Role;

@Getter
@Setter
public class AuthenticationDto {
    @NotEmpty(message = "the login field must not be empty")
    @Size(min = 2, max = 100, message = "from 2 to 100 symbols")
    private String login;

    @NotEmpty(message = "the password field must not be empty")
    @Size(min = 3, max = 100, message = "password must be between 3 and 100 characters")
    private String password;

}
