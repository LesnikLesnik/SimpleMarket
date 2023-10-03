package com.example.buysell.services;


import com.example.buysell.models.User;
import com.example.buysell.models.enums.Role;
import com.example.buysell.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//прописываем базовую логику регистрации
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder; //для шифрования паролей

    public boolean createUser(User user) { //регистрация пользователя
        String email = user.getEmail();
        if (userRepo.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль
        user.getRoles().add(Role.ADMIN);
        log.info("Saving new User with email: {}", email);
        userRepo.save(user);
        return true;
    }

    public List<User> list() {
        return userRepo.findAll();
    } //передаем список всем пользователей

    public void banUser(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.setActive(false);
            log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
        }
        userRepo.save(user);
    } //устанавливаем активность пользователя на false, что деактивирует его возможность делать что-то
}
