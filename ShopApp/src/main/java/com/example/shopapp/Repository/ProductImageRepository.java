package com.example.shopapp.Repository;

import com.example.shopapp.Model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{
    List<ProductImage> findAllByProductId(Long productId);
}
