package org.rbnk.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rbnk.auth.dto.ChangeRoleDto;
import org.rbnk.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/change")
    @ResponseStatus(HttpStatus.OK)
    public void changeRole(@RequestBody @Valid ChangeRoleDto changeRoleDto) {
        userService.changeRole(changeRoleDto);
    }

}
