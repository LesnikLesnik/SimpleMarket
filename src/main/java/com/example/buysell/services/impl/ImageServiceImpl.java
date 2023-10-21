package com.example.buysell.services.impl;

import com.example.buysell.dto.ProductDTO;
import com.example.buysell.entity.Image;
import com.example.buysell.entity.Product;
import com.example.buysell.services.ImageService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Сервис для работы с изображениями
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    /**
     * Преобразование файла в изображение
     */
    @Override
    public Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    /**
     * Добавление изображений к товару
     * @param product товар, к которому нужно прикрепить фотографии
     * @param image1 первое изображение будет отображаться на сайте
     */
    @Override
    public Product addImageToProduct(Product product,
                                     Image image1, Image image2, Image image3,
                                     MultipartFile file1, MultipartFile file2, MultipartFile file3){
        if (file1.getSize() != 0) {
            image1.setPreviewImage(true); //делаем изображение превьюшным
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            product.addImageToProduct(image3);
        }
        return product;
    }

    /*
     * будет добавлен функционал для сохранения изображения не в БД.
     * В БД будет передаваться только путь к файлу на диске
     */

}
