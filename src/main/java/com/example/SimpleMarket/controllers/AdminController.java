package com.example.SimpleMarket.controllers;


import com.example.SimpleMarket.entity.User;
import com.example.SimpleMarket.entity.enums.Role;
import com.example.SimpleMarket.services.UserService;
import com.example.SimpleMarket.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping
    public List<User> listUsers() {
        return userService.list();
    }

    @PostMapping("/{id}/ban")
    public void banUser(@PathVariable("id") Long id) {
        userService.banUser(id);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/edit")
    public void editUser(@PathVariable("id") Long id, @RequestParam Map<String, String> form) {
        User user = userService.getUserById(id);
        userService.changeUserRoles(user, form);
    }

//    @GetMapping("/admin/user/edit/{user}")
//    public String userEdit(@PathVariable("user") User user, Model model, Principal principal) {
//        model.addAttribute("user", user);
//        model.addAttribute("user", userService.getUserByPrincipal(principal));
//        model.addAttribute("roles", Role.values());
//        return "user-edit";
//    }
//
//    @PostMapping("/admin/user/edit")
//    public String userEdit(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
//        userService.changeUserRoles(user, form);
//        return "redirect:/admin";
//    }
}
