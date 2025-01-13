package org.rbnk.auth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rbnk.auth.dto.AuthenticationDto;
import org.rbnk.auth.dto.UserDto;
import org.rbnk.auth.exception.CustomAuthException;
import org.rbnk.auth.model.Role;
import org.rbnk.auth.model.User;
import org.rbnk.auth.security.PersonDetails;
import org.rbnk.auth.util.JwtUtil;
import org.rbnk.auth.validator.CustomValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CustomValidator customValidator;
    private final AuthenticationManager authenticationManager;

    public Map<String, String> registrationUser(UserDto userDto, BindingResult bindingResult) {
        User user = convertToUserAuth(userDto);
        customValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            log.warn("Registration failed " + errors);
            throw new CustomAuthException("Registration failed. " + errors);
        }
        userService.create(user);
        String token = jwtUtil.generateToken(user.getLogin(), user.getRole());
        log.info("Registration is completed " + user.getLogin());
        return Map.of("Jwt token", token);
    }

    public Map<String, String> loginUser(AuthenticationDto authenticationDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            log.warn("Login failed " + errors);
            throw new CustomAuthException("Login failed. " + errors);
        }
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.getLogin(),
                        authenticationDto.getPassword());
        Authentication authenticate;
        Collection<? extends GrantedAuthority> authorities;
        try {
            authenticate = authenticationManager.authenticate(authInputToken);
            authorities = authenticate.getAuthorities();
        } catch (BadCredentialsException e) {
            log.warn("Login failed for user " + authenticationDto.getLogin());
            throw new CustomAuthException("Incorrect login or password");
        }
        String role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new CustomAuthException("incorrect role"));
        String token = jwtUtil.generateToken(authenticationDto.getLogin(), Role.valueOf(role));
        log.info("Login is completed for user " + authenticationDto.getLogin());
        return Map.of("Jwt token", token);
    }

    private User convertToUserAuth(UserDto userDto) {
        return User.builder()
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .role(Role.ROLE_SUBSCRIBER)
                .build();
    }
}