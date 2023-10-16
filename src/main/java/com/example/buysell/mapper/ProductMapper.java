package com.example.buysell.mapper;


import com.example.buysell.dto.ProductDTO;
import com.example.buysell.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO map(Product product);
    List<ProductDTO> map(List<Product> products);
}
