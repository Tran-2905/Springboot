package com.example.shopapp.Service;

import com.example.shopapp.Dtos.ProductDTO;
import com.example.shopapp.Dtos.ProductImageDTO;
import com.example.shopapp.Model.Product;
import com.example.shopapp.Model.ProductImage;
import com.example.shopapp.Response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


public interface IProductService {
    public Product createProduct(ProductDTO product) throws Exception;
    public Product getProductById(Long id) throws Exception;
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
    public void deleteProductById(Long id);
    public Product updateProduct(Long id, ProductDTO product) throws Exception;
    public boolean existsByName(String name);
    public  ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
}
