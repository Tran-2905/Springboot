package com.example.shopapp.Service;

import com.example.shopapp.Dtos.ProductDTO;
import com.example.shopapp.Dtos.ProductImageDTO;
import com.example.shopapp.Model.Category;
import com.example.shopapp.Model.Product;
import com.example.shopapp.Model.ProductImage;
import com.example.shopapp.Repository.CategoryRepository;
import com.example.shopapp.Repository.ProductImageRepository;
import com.example.shopapp.Repository.ProductRepository;
import com.example.shopapp.Response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->new IllegalArgumentException("Category not found"));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .description(productDTO.getDescription())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long id) throws Exception{

        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {

        return productRepository.findAll(pageRequest).map(ProductResponse::fromProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            Category existingCateogry = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->new IllegalArgumentException("Category not found"));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCateogry);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            existingProduct.setDescription(productDTO.getDescription());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public boolean existsByName(String name) {

        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception{
        Product existingProduct = productRepository.findById(productId).orElseThrow(()->new Exception("Product not found"));
        ProductImage newProductImage = ProductImage.builder().product(existingProduct).imageUrl(productImageDTO.getImageUrl()).build();
        int size = productImageRepository.findAllByProductId(productId).size();
        if(size >= Product.MAX_IMAGES){
            throw new Exception("Product cannot have more than " + Product.MAX_IMAGES + " images");
        }
        return productImageRepository.save(newProductImage);
    }
}
