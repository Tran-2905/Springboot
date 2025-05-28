package com.example.shopapp.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fullname",nullable = false)
    private String fullName;

    @Column(name = "phone_number",nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "address",nullable = false, length = 200)
    private String address;

    @Column(name = "password",nullable = false, length = 100)
    private String password;

    private boolean isActive;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private String facebookAccountId;

    @Column(name = "google_account_id")
    private String googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
