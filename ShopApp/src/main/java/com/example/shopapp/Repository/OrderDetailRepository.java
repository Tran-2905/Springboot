package com.example.shopapp.Repository;

import com.example.shopapp.Model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrderId(Long orderId);
    List<OrderDetail> findAllByProductId(Long productId);
}
