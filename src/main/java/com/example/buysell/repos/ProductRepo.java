package com.example.buysell.repos;

import com.example.buysell.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> { //наследование от этого класса дает нам доступ к методам работы с продуктами

    List<Product> findByTitle(String title); //


}
