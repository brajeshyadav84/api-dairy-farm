package com.dairyFarm.dairyFarm.controller;

import com.dairyFarm.dairyFarm.dto.CategoryResponse;
import com.dairyFarm.dairyFarm.entity.Product;
import com.dairyFarm.dairyFarm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Value("${product.image.storage.path}")
    private String imageStoragePath;// Using interface

    @GetMapping
    public List<CategoryResponse> getAllProducts() {
        return productService.getAllProductsGroupedByCategory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping("/create/product")
//    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//        return ResponseEntity.ok(productService.createProduct(product));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(productDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create/product")
    public ResponseEntity<Product> createProduct(
            @RequestPart("product") Product product,
            @RequestPart("image") MultipartFile imageFile) {

        try {
            Product savedProduct = productService.createProduct(product);

            // Save the image
            if (!imageFile.isEmpty()) {
                String directoryPath = imageStoragePath + "/" + savedProduct.getId();
                Path dirPath = Paths.get(directoryPath);
                if (!Files.exists(dirPath)) {
                    Files.createDirectories(dirPath);
                }

                String filePath = directoryPath + "/" + imageFile.getOriginalFilename();
                File destinationFile = new File(filePath);
                imageFile.transferTo(destinationFile);

                // Update product with image URL
                savedProduct.setImageUrl(filePath);
                savedProduct = productService.updateProduct(savedProduct);
            }

            return ResponseEntity.ok(savedProduct);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
