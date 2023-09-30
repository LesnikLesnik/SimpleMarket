package com.example.buysell.services;

import com.example.buysell.models.Product;
import com.example.buysell.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepo productRepo;


    public List<Product> listProducts(String title) {
        List<Product> products = productRepo.findAll();
        if (title != null) return productRepo.findByTitle(title);
        return products;
    } //получение всего листа товаров

    public void saveProduct(Product product) {
        log.info("Saving new {}", product);
        productRepo.save(product);
    } //сохранение товара в листе товаров

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    } //удаление товара из листа товаров

    public Object getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    } //получение товара по id
}
