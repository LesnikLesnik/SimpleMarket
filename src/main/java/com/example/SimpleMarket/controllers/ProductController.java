package com.example.SimpleMarket.controllers;

import com.example.SimpleMarket.dto.ProductDTO;
import com.example.SimpleMarket.entity.Product;
import com.example.SimpleMarket.entity.User;
import com.example.SimpleMarket.services.ProductService;
import com.example.SimpleMarket.services.UserService;
import com.example.SimpleMarket.services.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final UserService userService;

    @GetMapping
    public List<ProductDTO> listProducts(@RequestParam(name = "title", required = false) String title) {
        return productService.getListProducts(title);
    }

    @GetMapping("/{id}")
    public ProductDTO productInfo(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/create")
    public void createProduct(@RequestBody ProductDTO productDTO, Principal principal) throws IOException {
        productService.saveProduct(principal, productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(userService.getUserByPrincipal(principal), id);
    }

    @GetMapping("/my-products")
    public List<Product> userProducts(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        return user.getProducts();
    }
}
