package com.example.buysell.repository;

import com.example.buysell.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); //для дальнейшей конфигурации security поиск user по userName
}
