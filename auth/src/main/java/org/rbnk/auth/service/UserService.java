package org.rbnk.auth.service;


import lombok.AllArgsConstructor;
import org.rbnk.auth.dto.ChangeRoleDto;
import org.rbnk.auth.exception.CustomAuthException;
import org.rbnk.auth.model.User;
import org.rbnk.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void create(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public Optional<User> findUser(String login) {
        return userRepository.findUserByLogin(login);
    }

    public void delete(String login) {
        Optional<User> userOptional = userRepository.findUserByLogin(login);
        if (userOptional.isPresent()) {
            long id = userOptional.get().getId();
            userRepository.deleteById(id);
        }
    }

    public void changeRole(ChangeRoleDto changeRoleDto) {
        User user = userRepository.findById(changeRoleDto.getId()).
                orElseThrow(() -> new CustomAuthException("User not found"));
        user.setRole(changeRoleDto.getRole());
        userRepository.save(user);
    }
}
