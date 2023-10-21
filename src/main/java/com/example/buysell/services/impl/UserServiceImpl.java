package com.example.buysell.services.impl;


import com.example.buysell.entity.User;
import com.example.buysell.entity.enums.Role;
import com.example.buysell.repository.UserRepository;
import com.example.buysell.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     * @param user
     * @param form
     */
    @Override
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values()) //проходимся по всем ролям
                .map(Role::name) //для каждой роли из коллекции выше мы вызываем метод name (преобразуем в строку)
                .collect(Collectors.toSet()); //преобразование всех ролей в сэт из строк
        user.getRoles().clear();
        for (String key : form.keySet()){
            if (roles.contains(key)){ //если роль содержит ту "роль", которую мы передаем в Мэп
                user.getRoles().add(Role.valueOf(key)); //тогда добавляем ее пользователю
            }
        }
        userRepository.save(user); //сохраняем в репозитории обновленные данные
    }
}
