package com.example.SimpleMarket.services.impl;

import com.example.SimpleMarket.dto.ProductDTO;
import com.example.SimpleMarket.entity.Image;
import com.example.SimpleMarket.entity.Product;
import com.example.SimpleMarket.entity.User;
import com.example.SimpleMarket.exceptions.ProductNotFoundException;
import com.example.SimpleMarket.exceptions.UserNotFoundException;
import com.example.SimpleMarket.mapper.ProductMapper;
import com.example.SimpleMarket.repository.ProductRepository;
import com.example.SimpleMarket.repository.UserRepository;
import com.example.SimpleMarket.services.ImageService;
import com.example.SimpleMarket.services.ProductService;
import com.example.SimpleMarket.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Сервис для работы с товарами
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    private final ProductMapper productMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private ImageService imageService;

    /**
     * возвращает отсортированный список товаров по названию (если оно задано)
     * @param title название товара
     * @return если не задан title, возвращает список всех товаров
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getListProducts(String title) {
        List<Product> products = getProducts(title);
        return products.stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    private List<Product> getProducts(String title) {
        return Optional.ofNullable(title)
                .map(productRepository::findByTitleLikeIgnoreCase)
                .orElseGet(productRepository::findAll);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getUserProductsAsDTO(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId + " не найден."));
        List<Product> products = user.getProducts();
        return products.stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    /**
     * Создаем (и сохраняем в БД) новый товар
     * @param principal активность пользователя
     * @param file1 форма для добавления 3 файлов, которые будут преобразованы в изображения
     */
    @Transactional
    @Override
    public void saveProduct(Principal principal, ProductDTO productDTO, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException { //3 формы для добавления фото
        // Создаем новый объект Product на основе данных из ProductDTO
        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCity(productDTO.getCity());
        product.setDateOfCreated(productDTO.getDateOfCreated());

        // Устанавливаем пользователя на основе Principal
        product.setUser(userService.getUserByPrincipal(principal));

        //переводим полученные файлы в изображения
        Image image1 = imageService.toImageEntity(file1);
        Image image2 = imageService.toImageEntity(file2);
        Image image3 = imageService.toImageEntity(file3);

        //добавляем изображения к товару
        imageService.addImageToProduct(product,
                image1, image2, image3);

        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), product.getUser().getEmail());
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId()); //получаем первую (превьюшную) фотографию
        productRepository.save(product); //сохраняем уже с фото
    }

    @Override
    @Transactional
    public void deleteProduct(User user, Long id) {
        productRepository.findById(id)
                .ifPresent(product -> {
                    if (product.getUser().getId().equals(user.getId())) {
                        productRepository.delete(product);
                        log.info("Product with id = {} was deleted", id);
                    } else {
                        log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
                    }
                });
        log.error("Product with id = {} is not found", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Товар с id: " + id + " не найден."));
        return productMapper.map(product);
    } //получение товара по id
}
