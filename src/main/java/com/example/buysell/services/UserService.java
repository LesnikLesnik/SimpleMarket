package com.example.buysell.services;


import com.example.buysell.models.User;
import com.example.buysell.models.enums.Role;
import com.example.buysell.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//прописываем базовую логику регистрации
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder; //для шифрования паролей

    public boolean createUser(User user) { //регистрация пользователя
        String email = user.getEmail();
        if (userRepo.findByEmail(email)!= null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //шифруем пароль
        user.getRoles().add(Role.USER);
        log.info("Saving new User with email: {}", email);
        return true;
    }
}
