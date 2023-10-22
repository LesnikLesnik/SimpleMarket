package com.example.SimpleMarket.repository;

import com.example.SimpleMarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * поиск товара по названию
     * @param title название товара
     */
    List<Product> findByTitleLikeIgnoreCase(String title);


}
