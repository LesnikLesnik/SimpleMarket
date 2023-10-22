package com.example.SimpleMarket.dto;

import com.example.SimpleMarket.entity.Image;
import com.example.SimpleMarket.entity.Product;
import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private Long id;

    private String email;

    private String phoneNumber;

    private String name;

    private Image avatar;

    private List<Product> products;

    public UserDTO(Long id, String email, String phoneNumber, String name, Image avatar, List<Product> products) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.avatar = avatar;
        this.products = products;
    }
}
