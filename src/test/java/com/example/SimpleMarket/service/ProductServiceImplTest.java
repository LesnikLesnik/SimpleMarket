package com.example.SimpleMarket.service;


import com.example.SimpleMarket.dto.ProductDTO;
import com.example.SimpleMarket.entity.Product;
import com.example.SimpleMarket.entity.User;
import com.example.SimpleMarket.mapper.ProductMapper;
import com.example.SimpleMarket.repository.ProductRepository;
import com.example.SimpleMarket.repository.UserRepository;
import com.example.SimpleMarket.services.ImageService;
import com.example.SimpleMarket.services.ProductService;
import com.example.SimpleMarket.services.UserService;
import com.example.SimpleMarket.services.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetListProducts() {
        // Создаем тестовый список товаров
        List<Product> testProducts = Arrays.asList(
                new Product(1L, "Product1", "Description1", 10, "City1", null, null, null, LocalDateTime.now()),
                new Product(2L, "Product2", "Description2", 15, "City2", null, null, null, LocalDateTime.now())
        );

        when(productRepository.findByTitleLikeIgnoreCase("Product")).thenReturn(testProducts);

        when(productMapper.map(testProducts.get(0))).thenReturn(new ProductDTO(1L, "Product1", "Description1", 10, "City1", null, 1L, LocalDateTime.now()));
        when(productMapper.map(testProducts.get(1))).thenReturn(new ProductDTO(2L, "Product2", "Description2", 15, "City2", null, 2L, LocalDateTime.now()));

        List<ProductDTO> result = productService.getListProducts("Product");

        assertEquals(2, result.size());
        assertEquals("Product1", result.get(0).getTitle());
        assertEquals("Description1", result.get(0).getDescription());
        assertEquals(10, result.get(0).getPrice());
        assertEquals("City1", result.get(0).getCity());
        assertEquals(1L, result.get(0).getPreviewImageId());
        assertEquals("Product2", result.get(1).getTitle());
        assertEquals("Description2", result.get(1).getDescription());
        assertEquals(15, result.get(1).getPrice());
        assertEquals("City2", result.get(1).getCity());
        assertEquals(2L, result.get(1).getPreviewImageId());
    }

    @Test
    public void testGetUserProductsAsDTO() {
        User testUser = new User();
        testUser.setId(1L);

        List<Product> testProducts = Arrays.asList(
                new Product(1L, "Product1", "Description1", 10, "City1", null, null, testUser, LocalDateTime.now()),
                new Product(2L, "Product2", "Description2", 15, "City2", null, null, testUser, LocalDateTime.now())
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        when(productMapper.map(testProducts.get(0))).thenReturn(new ProductDTO(1L, "Product1", "Description1", 10, "City1", null, 1L, LocalDateTime.now()));
        when(productMapper.map(testProducts.get(1))).thenReturn(new ProductDTO(2L, "Product2", "Description2", 15, "City2", null, 2L, LocalDateTime.now()));

        testUser.setProducts(testProducts);

        List<ProductDTO> result = productService.getUserProductsAsDTO(1L);

        assertEquals(2, result.size());
        assertEquals("Product1", result.get(0).getTitle());
        assertEquals("Description1", result.get(0).getDescription());
        assertEquals(10, result.get(0).getPrice());
        assertEquals("City1", result.get(0).getCity());
        assertEquals(1L, result.get(0).getPreviewImageId());
        assertEquals("Product2", result.get(1).getTitle());
        assertEquals("Description2", result.get(1).getDescription());
        assertEquals(15, result.get(1).getPrice());
        assertEquals("City2", result.get(1).getCity());
        assertEquals(2L, result.get(1).getPreviewImageId());
    }


}
