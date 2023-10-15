package com.example.buysell.controllers;


import com.example.buysell.entity.User;
import com.example.buysell.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model){
        if (!userServiceImpl.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с " + user.getEmail() + "уже существует!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/user/{user}")
   public String userInfo(@PathVariable("user") User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "user-info";
   }
}
