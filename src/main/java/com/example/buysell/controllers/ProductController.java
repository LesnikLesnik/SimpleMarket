package com.example.buysell.controllers;

import com.example.buysell.models.Product;
import com.example.buysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService PRODUCT_SERVICE;


    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("products", PRODUCT_SERVICE.listProducts()); //передаем список всех товаров по ключу
        return "products";
    }

    @GetMapping("/product/{id}")
    public String inform(@PathVariable Long id, Model model) {
        model.addAttribute("product", PRODUCT_SERVICE.getProductById(id));
        return "product-info";
    }
    @PostMapping("/product/create")
    public String createProduct(Product product) {
        PRODUCT_SERVICE.saveProduct(product);
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        PRODUCT_SERVICE.deleteProduct(id);
        return "redirect:/";
    }
}
