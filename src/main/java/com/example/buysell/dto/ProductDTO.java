package com.example.buysell.dto;

import com.example.buysell.entity.Image;
import com.example.buysell.entity.Product;
import lombok.*;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDTO {


    private Long id;

    private String title;

    private String description;

    private int price;

    private String city;

    private List<Image> images;

    private Long previewImageId;

    @Builder
    public ProductDTO(@NonNull Long id, String title, String description, int price, String city, List<Image> images, Long previewImageId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
        this.images = images;
        this.previewImageId = previewImageId;
    }
}
