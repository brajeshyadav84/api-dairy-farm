package com.dairyFarm.dairyFarm.Impl;

import com.dairyFarm.dairyFarm.dto.CategoryResponse;
import com.dairyFarm.dairyFarm.dto.ProductResponse;
import com.dairyFarm.dairyFarm.entity.Product;
import com.dairyFarm.dairyFarm.repository.ProductRepository;
import com.dairyFarm.dairyFarm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Value("${product.image.storage.path}")
    private String imageStoragePath;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product productDetails) {
        return productRepository.findById(productDetails.getId()).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setCategory(productDetails.getCategory());
            product.setPrice(productDetails.getPrice());
            product.setUnit(productDetails.getUnit());
            product.setStockQuantity(productDetails.getStockQuantity());
            product.setImageUrl(productDetails.getImageUrl());
            product.setExpiryDate(productDetails.getExpiryDate());
            product.setBrand(productDetails.getBrand());
            product.setWeight(productDetails.getWeight());
            product.setUpdatedAt(java.time.LocalDateTime.now());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + productDetails.getId()));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponse> getAllProductsGroupedByCategory() {
        List<Product> products = productRepository.findAll();

        // Group products by category
        Map<String, List<Product>> groupedProducts = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        // Convert grouped products to response format
        return groupedProducts.entrySet().stream()
                .map(entry -> CategoryResponse.builder()
                        .categoryName(entry.getKey())
                        .categoryData(entry.getValue().stream().map(product ->
                                ProductResponse.builder()
                                        .id(product.getId())
                                        .name(product.getName())
                                        .image(imageStoragePath + "/" + product.getId()) // Construct image URL dynamically
                                        .price(product.getPrice())
                                        .quantity(product.getUnit())
                                        .discount(0) // Set discount logic if applicable
                                        .count(1)
                                        .isSoldOut(product.getStockQuantity() == 0)
                                        .build()
                        ).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
