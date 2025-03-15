package com.dairyFarm.dairyFarm.repository;

import com.dairyFarm.dairyFarm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
