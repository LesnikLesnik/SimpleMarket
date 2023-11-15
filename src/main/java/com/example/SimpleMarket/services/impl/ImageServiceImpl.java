package com.example.SimpleMarket.services.impl;

import com.example.SimpleMarket.entity.Image;
import com.example.SimpleMarket.entity.Product;
import com.example.SimpleMarket.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * Сервис для работы с изображениями
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    /**
     * Преобразование файла в изображение
     */

    private final String imageDirectory = "C:\\ProjectsPhoto";
    @Override
    public Image toImageEntity(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path destinationFilePath = Paths.get(imageDirectory + fileName);
        Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING); // Сохраняем файл на диск

        Image image = new Image();
        image.setName(fileName);
        image.setOriginalFileName(fileName);
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setPath(destinationFilePath.toString()); // Сохраняем путь к файлу

        return image;
    }

    /**
     * Добавление изображений к товару
     * @param product товар, к которому нужно прикрепить фотографии
     * @param image1 первое изображение будет отображаться на сайте
     */
    @Override
    public Product addImageToProduct(Product product, Image image1, Image image2, Image image3) {
        if (image1 != null) {
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (image2 != null) {
            product.addImageToProduct(image2);
        }
        if (image3 != null) {
            product.addImageToProduct(image3);
        }
        return product;
    }


}
