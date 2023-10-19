package com.example.buysell.services;

import com.example.buysell.dto.ProductDTO;
import com.example.buysell.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ProductService {
    List<ProductDTO> listProducts(String title);
    void deleteProduct(Long id);

    Object getProductById(Long id);

    void saveProduct(Principal principal, ProductDTO productDTO, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException;
}
