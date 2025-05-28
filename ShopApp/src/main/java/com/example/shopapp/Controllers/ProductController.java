package com.example.shopapp.Controllers;

import ch.qos.logback.core.util.StringUtil;
import com.example.shopapp.Dtos.ProductDTO;
import com.example.shopapp.Dtos.ProductImageDTO;
import com.example.shopapp.Model.Product;
import com.example.shopapp.Model.ProductImage;
import com.example.shopapp.Response.ProductListResponse;
import com.example.shopapp.Response.ProductResponse;
import com.example.shopapp.Service.IProductService;
import com.example.shopapp.Service.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;
    @PostMapping()
    public ResponseEntity<?> createProduct(@Valid @RequestBody   ProductDTO productDTO, BindingResult result
//            @ModelAttribute("files") List<MultipartFile> files
            ){
        try {
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }
            Product newProduct = productService.createProduct(productDTO);

            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping( value ="uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long productId, @RequestParam("files") List<MultipartFile> files ){
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            List<ProductImage> productImages = new ArrayList<>();
            if(files.size() > Product.MAX_IMAGES){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product cannot have more than " + Product.MAX_IMAGES + " images");
            }
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Image size must be less than 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Image must be a jpeg or png file");
                }
                String fileName = storeFile(file);
                ProductImage productImage = productService.createProductImage(existingProduct.getId(), ProductImageDTO.builder().imageUrl(fileName).build());
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }


    public String storeFile(MultipartFile file) throws IOException {
        if(isImageFile(file)||(file.getOriginalFilename() == null)){
            throw new IOException("Invalid file type");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        them UUID vao file name de dam bao ten file la duy nhat
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        // duong dan den thu muc ma ban muon luu file
        java.nio.file.Path upLoadDir = Paths.get("uploads");
        //kiem tra xem thu muc ma ban muon luu file da ton tai hay chua
        if(!Files.exists(upLoadDir)){
            Files.createDirectories(upLoadDir);
        }
        // duong dan den file
        java.nio.file.Path destination = Paths.get(upLoadDir.toString(), uniqueFileName);
//        sao chep file vao thu muc
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    @GetMapping("")
    public ResponseEntity<?> getProduct( @RequestParam("Page") int page, @RequestParam("Size") int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(PageRequest.of(page, size));
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder().totalPages(totalPages).products(products).build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@RequestParam("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body("Product with id = " + id);
    }

//    @PostMapping("/generateFakeproduct")
    private ResponseEntity<?> generateFakeproduct() {
        Faker faker = new Faker();
        for(int i = 0; i < 1000000; i++){
            String productName = faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }

            ProductDTO productDTO = ProductDTO.builder().name(productName)
                    .price(faker.number().numberBetween(10,900000000))
                    .description(faker.lorem().paragraph())
                    .categoryId((long) faker.number().numberBetween(2,5))
                    .description(faker.lorem().paragraph())
                    .thumbnail(faker.internet().image())
                    .build() ;
            try{
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }

        }
        return ResponseEntity.ok("Generate fake product successfully");
    }

}
/*{
    "name": "Ipad pro 2023",
    "price": 252653,
    "thumbnail": "",
    "description": "This is a tests product",
    "category_id": 1

}*/
