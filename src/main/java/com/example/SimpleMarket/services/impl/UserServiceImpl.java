package com.example.SimpleMarket.services.impl;


import com.example.SimpleMarket.entity.User;
import com.example.SimpleMarket.entity.enums.Role;
import com.example.SimpleMarket.exceptions.UserNotFoundException;
import com.example.SimpleMarket.repository.UserRepository;
import com.example.SimpleMarket.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователями
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; //для шифрования паролей

    /**
     * Регистрация пользователя
     */
    @Override
    @Transactional
    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль
        user.getRoles().add(Role.USER);
        log.info("Saving new User with email: {}", email);
        userRepository.save(user);
        return true;
    }

    /**
     * Получение списка всех пользователей
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * Бан (деактивация) пользователя
     * @param id The id of the user
     */
    @Override
    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null); //находим пользователя по айди
        if (user != null) {
            if (user.isActive()) { //если юзер активен
                user.setActive(false); //устанавливаем активность пользователя на false, что деактивирует его возможность делать что-либо
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true); //ставим активность на 1 (разбан)
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        userRepository.save(user);
    }

    /**
     * Изменение роли пользователя (всего доступно две роли: Админ и Юзер)
     * @param user пользователь, которому меняем роль
     * @param form устанавливаемая роль
     */
    @Override
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<Role> roles = Arrays.stream(Role.values())
                .filter(role -> form.containsKey(role.name()))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с " + id + " не найден."));
    }

//    @Override
//    public boolean authenticateUser(String email, String password) {
//        User user = userRepository.findByEmail(email);
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            return true;
//        }
//        return false;
//    }


    public User getUserByPrincipal(Principal principal) {
        return Optional.ofNullable(principal)
                .map(Principal::getName)
                .map(userRepository::findByEmail)
                .orElseGet(User::new);
    }
}
