package org.rbnk.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rbnk.auth.dto.AuthenticationDto;
import org.rbnk.auth.dto.UserDto;
import org.rbnk.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API",
        description = "API for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Register a new user",
            description = "Register a new user with username, password.")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDto userDto,
                                                                  BindingResult bindingResult) {
        return authService.registrationUser(userDto, bindingResult);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "User login",
            description = "Authenticate a user and generate a JWT token.")
    public Map<String, String> performLogin(@RequestBody @Valid AuthenticationDto authenticationDto,
                                            BindingResult bindingResult) {
        return authService.loginUser(authenticationDto, bindingResult);
    }
}


