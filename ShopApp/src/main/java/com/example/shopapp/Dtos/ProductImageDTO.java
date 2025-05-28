package com.example.shopapp.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
   @JsonProperty("product_id")
   @Min(value = 1, message = "Product id must be greater than or equal to 1")
    private Long productid;

    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "Image url must be at least 10 characters long")
    private String imageUrl;
}
