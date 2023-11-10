package com.example.SimpleMarket.services;

import com.example.SimpleMarket.entity.Image;
import com.example.SimpleMarket.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    Image toImageEntity(MultipartFile file) throws IOException;

    Product addImageToProduct(Product product,
                              Image image1, Image image2, Image image3);
}
