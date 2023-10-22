package com.example.SimpleMarket.mapper;


import com.example.SimpleMarket.dto.ProductDTO;
import com.example.SimpleMarket.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO map(Product product);
    List<ProductDTO> map(List<Product> products);
}
