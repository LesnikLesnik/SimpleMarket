package com.example.buysell.services.impl;


import com.example.buysell.entity.User;
import com.example.buysell.entity.enums.Role;
import com.example.buysell.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//прописываем базовую логику регистрации
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder; //для шифрования паролей

    public boolean createUser(User user) { //регистрация пользователя
        String email = user.getEmail();
        if (userRepo.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль
        user.getRoles().add(Role.USER);
        log.info("Saving new User with email: {}", email);
        userRepo.save(user);
        return true;
    }

    public List<User> list() {
        return userRepo.findAll();
    } //передаем список всем пользователей

    public void banUser(Long id) {
        User user = userRepo.findById(id).orElse(null); //находим пользователя по айди
        if (user != null) {
            if (user.isActive()) { //если юзер активен
                user.setActive(false); //ставим активность на 0 (бан)
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true); //ставим активность на 1 (разбан)
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        userRepo.save(user);
    } //устанавливаем активность пользователя на false, что деактивирует его возможность делать что-то

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values()) //проходимся по всем ролям
                .map(Role::name) //для каждой роли из коллекции выше мы вызываем метод name (преобразуем в строку)
                .collect(Collectors.toSet()); //преобразование всех ролей в сэт из строк
        user.getRoles().clear(); //очищаем все роли
        for (String key : form.keySet()){
            if (roles.contains(key)){ //если роль содержит ту "роль", которую мы передаем в Мэп
                user.getRoles().add(Role.valueOf(key)); //тогда добавляем ее пользователю
            }
        }
        userRepo.save(user); //сохраняем в репозитории обновленные данные
    }
}
