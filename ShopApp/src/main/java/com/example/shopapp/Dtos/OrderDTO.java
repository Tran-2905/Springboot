package com.example.shopapp.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    @Min(value = 1, message = "user id must be greater than or equal to 1")
    @JsonProperty("user_id")
    private long userId;

    private String fullname;

    @NotBlank(message = "Email can not be null")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Phone number can not be null")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    private String note;


    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    private float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("shipping_date")
    private Date shippingDate;
}
