package com.example.shopapp.Service;

import com.example.shopapp.Dtos.OrderDTO;
import com.example.shopapp.Model.Order;
import com.example.shopapp.Model.User;

import java.util.List;

public interface IOrderService {
    public List<Order> getByUserId( Long userId );
    public Order getOrderById(long id);
    public Order updateOrder(Long id , OrderDTO orderDTO);
    public void deleteOrder(long id);
    public Order createOrder(OrderDTO orderDTO);
}
