package com.example.shopapp.Service;

import com.example.shopapp.Dtos.OrderDTO;
import com.example.shopapp.Model.Order;
import com.example.shopapp.Model.OrderStatus;
import com.example.shopapp.Model.User;
import com.example.shopapp.Repository.OrderRepository;
import com.example.shopapp.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public List<Order> getByUserId(Long userId) {
        return orderRepository.findByUserId_Id(userId);
    }

    @Override
    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(()-> new RuntimeException("User " + orderDTO.getUserId() + " Not Found" ));
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        Date shipDate = orderDTO.getShippingDate() == null ? Date.valueOf(LocalDate.now()) : orderDTO.getShippingDate();

        if(shipDate.before(Date.valueOf(LocalDate.now()))){
            throw new RuntimeException("Date must be after order date");
        }
        order.setShippingDate(shipDate);
        order.setActive(true);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws RuntimeException {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order " + id + " Not Found"));

        User existingUser = userRepository.findById(existingOrder.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Configure mapper to skip setId and setUser
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> {
            mapper.skip(Order::setId);
            mapper.skip(Order::setUser);
        });
        modelMapper.map(orderDTO, existingOrder);
        existingOrder.setOrderDate(Date.valueOf(LocalDate.now()));
        existingOrder.setUser(existingUser);
        existingOrder.setStatus(OrderStatus.PENDING);
        existingOrder.setActive(true);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order != null){
            order.setActive(false);
            orderRepository.save(order);
        }
    }
}
