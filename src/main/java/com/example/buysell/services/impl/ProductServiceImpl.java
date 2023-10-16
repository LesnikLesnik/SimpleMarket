package com.example.buysell.services.impl;

import com.example.buysell.dto.ProductDTO;
import com.example.buysell.entity.Image;
import com.example.buysell.entity.Product;
import com.example.buysell.entity.User;
import com.example.buysell.exceptions.ProductNotFoundException;
import com.example.buysell.mapper.ProductMapper;
import com.example.buysell.repos.ProductRepo;
import com.example.buysell.repos.UserRepo;
import com.example.buysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepo productRepo;

    @Autowired
    private final ProductMapper mapper;
    private final UserRepo userRepo;

    private final ImageServiceImpl imageService;

    public List<ProductDTO> listProducts(String title) {
        List<Product> products;
        if (title != null) {
            products = productRepo.findByTitle(title);
        } else {
            products = productRepo.findAll();
        }
        return mapper.map(products);
    } //получение всего листа товаров

    public void saveProduct(Principal principal, ProductDTO productDTO, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException { //3 формы для добавления фото
        // Создаем новый объект Product на основе данных из ProductDTO
        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCity(productDTO.getCity());
        product.setDateOfCreated(productDTO.getDateOfCreated());

        // Устанавливаем пользователя на основе Principal
        product.setUser(getUserByPrincipal(principal));

        //переводим полученные файлы в изображения
        Image image1 = imageService.toImageEntity(file1);
        Image image2 = imageService.toImageEntity(file2);
        Image image3 = imageService.toImageEntity(file3);

        //добавляем изображения к товару
        imageService.addImageToProduct(product, image1, file1, image2, file2, image3, file3);

        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), product.getUser().getEmail());
        Product productFromDb = productRepo.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId()); //получаем первую (превьюшную) фотографию
        productRepo.save(product); //сохраняем уже с фото
    } //сохранение товара в листе товаров

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepo.findByEmail(principal.getName());
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    } //удаление товара из листа товаров

    public Object getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Товар с id: " + id + "не найден."));
    } //получение товара по id
}
