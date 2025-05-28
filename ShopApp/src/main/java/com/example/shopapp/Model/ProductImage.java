package com.example.shopapp.Model;

import com.example.shopapp.Dtos.ProductImageDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {
    public static final int MAXIMUM_PICTURE = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "image_url")
    private String imageUrl;

}
