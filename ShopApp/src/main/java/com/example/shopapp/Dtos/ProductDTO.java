package com.example.shopapp.Dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotNull(message = "Product name can not be null")
    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters")
    private String name;
    @NotNull(message = "Product price can not be null")
    @Min(value = 0, message = "Product price must be greater than or equal to 0")
    @Max(value = 10000000, message = "Product price must be less than or equal to 10000000")
    private double price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private int categoryId;
    private List<MultipartFile> files;
}
