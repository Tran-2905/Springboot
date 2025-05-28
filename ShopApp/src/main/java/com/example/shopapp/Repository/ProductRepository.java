package com.example.shopapp.Repository;

import com.example.shopapp.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;

public interface ProductRepository extends JpaRepository <Product, Long>{

    boolean existsByName(String name);
    Page <Product> findAllByCategoryId(Long categoryId, Pageable pageable);
}
