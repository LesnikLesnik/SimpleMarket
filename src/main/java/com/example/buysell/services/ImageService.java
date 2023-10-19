package com.example.buysell.services;

import com.example.buysell.entity.Image;
import com.example.buysell.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    Image toImageEntity(MultipartFile file) throws IOException;

    Product addImageToProduct(Product product, Image image1, MultipartFile file1,
                              Image image2, MultipartFile file2,
                              Image image3, MultipartFile file3);
}
