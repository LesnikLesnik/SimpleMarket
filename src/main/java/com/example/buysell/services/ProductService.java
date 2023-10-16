package com.example.buysell.services;

import com.example.buysell.dto.ProductDTO;
import com.example.buysell.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> listProducts(String title);
}
