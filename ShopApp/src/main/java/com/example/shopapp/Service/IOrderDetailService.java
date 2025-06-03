package com.example.shopapp.Service;

import com.example.shopapp.Dtos.OrderDetailDTO;
import com.example.shopapp.Model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception;
    List<OrderDetail> findByOrderId(Long id);
    OrderDetail getOrderDetail(Long id) throws Exception;
    void deleteById(Long id);

}
