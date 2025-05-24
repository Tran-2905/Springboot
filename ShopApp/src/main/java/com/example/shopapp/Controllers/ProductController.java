package com.example.shopapp.Controllers;

import ch.qos.logback.core.util.StringUtil;
import com.example.shopapp.Dtos.ProductDTO;
import jakarta.validation.Valid;
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
@RequestMapping("${api.prefix}/products")
public class ProductController {
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute   ProductDTO product, BindingResult result /*,@RequestPart("imgFile") MultipartFile imgFile */){
        try {
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }
            List <MultipartFile> files = product.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for(MultipartFile file : files){
                if(file.getSize() > 10*1024*1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Image size must be less than 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Image must be a jpeg or png file");
                }
                String fileName = storeFile(file);
                product.setThumbnail(fileName);
            }
            return ResponseEntity.ok("Create Product-----" + product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public String storeFile(MultipartFile file) throws IOException {
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
    public ResponseEntity<String> getProduct( @RequestParam("Page") int page, @RequestParam("Size") int size){
        return ResponseEntity.ok("All Products");
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@RequestParam("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body("Product with id = " + id);
    }

}
/*{
    "name": "Ipad pro 2023",
    "price": 252653,
    "thumbnail": "",
    "description": "This is a tests product",
    "category_id": 1

}*/
