package com.example.SimpleMarket.repository;

import com.example.SimpleMarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); //для дальнейшей конфигурации security поиск user по userName
}
