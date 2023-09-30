package com.example.buysell.services;

import com.example.buysell.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();
    private long ID = 0;

    {
        products.add(new Product(++ID, "PS5", "Product 1", 101, "Moscow", "Andrew"));
        products.add(new Product(++ID, "Iphone5", "Product 2", 102, "Saint-Petersburg", "Kolyan"));
    }

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
