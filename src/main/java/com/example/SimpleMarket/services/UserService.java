package com.example.SimpleMarket.services;

import com.example.SimpleMarket.dto.ProductDTO;
import com.example.SimpleMarket.entity.User;

import java.security.Principal;
import java.util.List;
import java.util.Map;


public interface UserService {
    boolean createUser(User user);

    List<User> list();

    void banUser(Long id);

    void changeUserRoles(User user, Map<String, String> form);
    User getUserByPrincipal(Principal principal);


}
