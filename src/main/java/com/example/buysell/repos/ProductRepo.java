package com.example.buysell.repos;

import com.example.buysell.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> { //наследование от этого класса дает нам доступ к методам работы с продуктами
}
