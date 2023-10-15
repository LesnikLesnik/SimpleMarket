package com.example.buysell.controllers.dto;

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



}
