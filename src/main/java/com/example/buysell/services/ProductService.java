package com.example.buysell.services;

import com.example.buysell.dto.ProductDTO;
import com.example.buysell.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getListProducts(String title);
    void deleteProduct(User user, Long id);

    Object getProductById(Long id);

    void saveProduct(Principal principal, ProductDTO productDTO, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException;
}
