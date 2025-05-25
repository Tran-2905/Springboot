package com.example.shopapp.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_account")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "provider_id", length = 20)
    private String providerId;

    @Column(name = "provider", length = 20)
    private String provider;

    @Column(name = "email")
    private String email;

    @Column(name ="name", length= 100)
    private String name;
}
