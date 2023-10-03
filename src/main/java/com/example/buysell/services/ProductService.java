package com.example.buysell.services;

import com.example.buysell.models.Image;
import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.repos.ProductRepo;
import com.example.buysell.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepo productRepo;

    private final UserRepo userRepo;

    public List<Product> listProducts(String title) {
        List<Product> products = productRepo.findAll();
        if (title != null) return productRepo.findByTitle(title);
        return products;
    } //получение всего листа товаров

    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException { //3 формы для добавления фото
        product.setUser(getUserByPrincipal(principal));
        Image image1 = toImageEntity(file1);
        Image image2 = toImageEntity(file2);
        Image image3 = toImageEntity(file3);
        if (file1.getSize() != 0) { //если фото имеется, преобразовываем из мультипарт в фото
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            product.addImageToProduct(image3);
        }
        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), product.getUser().getEmail());
        Product productFromDb = productRepo.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId()); //получаем первую (превьюшную) фотографию
        productRepo.save(product); //сохраняем уже с фото
    } //сохранение товара в листе товаров

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepo.findByEmail(principal.getName());
    }

    public Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    } //удаление товара из листа товаров

    public Object getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    } //получение товара по id
}
