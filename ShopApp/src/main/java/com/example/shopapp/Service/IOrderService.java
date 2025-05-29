package com.example.shopapp.Service;

import com.example.shopapp.Dtos.OrderDTO;
import com.example.shopapp.Model.Order;
import com.example.shopapp.Response.OrderResponse;

import java.util.List;

public interface IOrderService {
    public List<OrderResponse> getAllOrders();
    public OrderResponse getOrderById(int id);
    public OrderResponse updateOrder(Order order);
    public void deleteOrder(int id);
    public OrderResponse createOrder(OrderDTO orderDTO);
}
