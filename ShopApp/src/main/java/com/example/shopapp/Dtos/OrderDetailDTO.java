package com.example.shopapp.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    @Min(value = 1, message = "Order id must be greater than or equal to 1")
    @JsonProperty("order_id")
    private long orderId;

    @Min(value = 1, message = "Product id must be greater than or equal to 1")
    @JsonProperty("product_id")
    private long productId;

    @Min(value = 0, message = "Product quantity must be greater than or equal to 0")
    private double price;

    @Min(value = 1, message = "Product quantity must be greater than or equal to 1")
    @JsonProperty("number_of_product")
    private int numberOfProduct;

    @Min(value = 0, message = "Product quantity must be greater than or equal to 0")
    @JsonProperty("total_money")
    private double totalMoney;

    private String color;
}
