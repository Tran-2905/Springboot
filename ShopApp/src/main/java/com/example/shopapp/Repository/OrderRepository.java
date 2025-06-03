package com.example.shopapp.Repository;

import com.example.shopapp.Model.Order;
import com.example.shopapp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository <Order, Long>{
    List<Order> findByUserId_Id(Long userId);
}
