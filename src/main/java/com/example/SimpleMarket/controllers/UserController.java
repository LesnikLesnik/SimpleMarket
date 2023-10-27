package com.example.SimpleMarket.controllers;


import com.example.SimpleMarket.dto.ProductDTO;
import com.example.SimpleMarket.entity.Product;
import com.example.SimpleMarket.entity.User;
import com.example.SimpleMarket.services.ProductService;
import com.example.SimpleMarket.services.UserService;
import com.example.SimpleMarket.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/profile")
    public User getProfile(Principal principal) {
        return userService.getUserByPrincipal(principal);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (!userService.createUser(user)) {
            return ResponseEntity.badRequest().body("Пользователь с таким email уже существует.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно создан.");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (userService.authenticateUser(user.getEmail(), user.getPassword())) {
            return ResponseEntity.ok("Аутентификация успешна.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка аутентификации.");
        }
    }

    @GetMapping("/{id}/products")
    public List<ProductDTO> getUserProducts(@PathVariable Long id) {
        return productService.getUserProductsAsDTO(id);
    }
}

