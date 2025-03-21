package com.dairyFarm.dairyFarm.service;

import com.dairyFarm.dairyFarm.dto.CategoryResponse;
import com.dairyFarm.dairyFarm.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product productDetails);
    void deleteProduct(Long id);

    List<CategoryResponse> getAllProductsGroupedByCategory();
}
