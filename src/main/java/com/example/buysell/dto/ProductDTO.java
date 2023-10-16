package com.example.buysell.dto;

import com.example.buysell.entity.Image;
import lombok.*;

import java.time.LocalDateTime;
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

    private LocalDateTime dateOfCreated;

    @Builder
    public ProductDTO(@NonNull Long id, String title, String description, int price, String city, List<Image> images, Long previewImageId, LocalDateTime dateOfCreated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
        this.images = images;
        this.previewImageId = previewImageId;
        this.dateOfCreated = dateOfCreated;
    }
}
