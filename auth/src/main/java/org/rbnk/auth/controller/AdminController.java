package org.rbnk.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rbnk.auth.dto.ChangeRoleDto;
import org.rbnk.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin API", description = "API for administrative actions")
public class AdminController {

    private final UserService userService;

    @PostMapping("/change")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change user role",
            description = "Change the role of a user by providing the username and the new role.")
    public void changeRole(@RequestBody @Valid ChangeRoleDto changeRoleDto) {
        userService.changeRole(changeRoleDto);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all users",
            description = "Retrieve a list of all registered user logins.")
    public List<String> getLoginUsers() {
        return userService.findAll();
    }

}
