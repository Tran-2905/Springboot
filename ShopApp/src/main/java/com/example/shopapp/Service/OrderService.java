package com.example.shopapp.Service;

import com.example.shopapp.Dtos.OrderDTO;
import com.example.shopapp.Model.Order;
import com.example.shopapp.Model.OrderStatus;
import com.example.shopapp.Model.User;
import com.example.shopapp.Repository.OrderRepository;
import com.example.shopapp.Repository.UserRepository;
import com.example.shopapp.Response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderResponse> getAllOrders() {
        return List.of();
    }

    @Override
    public OrderResponse getOrderById(int id) {
        return null;
    }

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(()-> new RuntimeException("User " + orderDTO.getUserId() + " Not Found" ));
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        order.setUserId(user);
        order.setStatus(OrderStatus.PENDING);
        Date shipDate = orderDTO.getShippingDate() == null ? Date.valueOf(LocalDate.now()) : orderDTO.getShippingDate();

        if(shipDate.before(Date.valueOf(LocalDate.now()))){
            throw new RuntimeException("Date must be after order date");
        }
        order.setShippingDate(shipDate);
        order.setActive(true);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse updateOrder(Order order) {
        return null;
    }

    @Override
    public void deleteOrder(int id) {

    }
}
