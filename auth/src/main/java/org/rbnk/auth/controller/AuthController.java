package org.rbnk.auth.controller;

import org.rbnk.auth.dto.AuthenticationDto;
import org.rbnk.auth.dto.UserDto;
import org.rbnk.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> performRegistration(@RequestBody @Valid UserDto userDto,
                                                                  BindingResult bindingResult) {
        return authService.registrationUser(userDto, bindingResult);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> performLogin(@RequestBody @Valid AuthenticationDto authenticationDto,
                                            BindingResult bindingResult) {
        return authService.loginUser(authenticationDto, bindingResult);
    }
}


