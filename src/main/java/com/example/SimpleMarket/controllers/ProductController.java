package com.example.SimpleMarket.controllers;

import com.example.SimpleMarket.dto.ProductDTO;
import com.example.SimpleMarket.entity.Image;
import com.example.SimpleMarket.entity.Product;
import com.example.SimpleMarket.entity.User;
import com.example.SimpleMarket.services.ProductService;
import com.example.SimpleMarket.services.UserService;
import com.example.SimpleMarket.services.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;


    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Principal principal, Model model) {
        List<ProductDTO> productDTOList = productService.getListProducts(title);
        User userByPrincipal = userService.getUserByPrincipal(principal);

        model.addAttribute("products", productDTOList);
        model.addAttribute("user", userByPrincipal);
        model.addAttribute("searchWord", title);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = (Product) productService.getProductById(id);
        User userByPrincipal = userService.getUserByPrincipal(principal);
        List<Image> productImages = product.getImages();
        User author = product.getUser();

        model.addAttribute("user", userByPrincipal);
        model.addAttribute("product", product);
        model.addAttribute("images", productImages);
        model.addAttribute("authorProduct", author);
        return "product-info";
    }
    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                ProductDTO productDTO, Principal principal) throws IOException {
        productService.saveProduct(principal, productDTO, file1, file2, file3);
        return "redirect:/my/products";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(userService.getUserByPrincipal(principal), id);
        return "redirect:/my/products";
    }

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        List<Product> products = user.getProducts();

        model.addAttribute("user", user);
        model.addAttribute("products", products);
        return "my-products";
    }
}
