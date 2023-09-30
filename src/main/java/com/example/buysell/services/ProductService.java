package com.example.buysell.services;

import com.example.buysell.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();


    public List<Product> listProducts() {
        return products;
    } //получение всего листа товаров

    public void saveProduct(Product product) {
        product.setId(++ID);
        products.add(product);
    } //сохранение товара в листе товаров

    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    } //удаление товара из листа товаров

    public Object getProductById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}
