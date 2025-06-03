package com.example.shopapp.Controllers;

import com.example.shopapp.Dtos.OrderDTO;
import com.example.shopapp.Model.Order;
import com.example.shopapp.Model.User;
import com.example.shopapp.Service.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/adds")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO , BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }
            Order orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") long id){
        try{
            Order existingOrder = orderService.getOrderById(id);
            return ResponseEntity.ok(existingOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getOrders(@PathVariable("user_id") long user_Id){
        try{
            List<Order> orders = orderService.getByUserId(user_Id);
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") long id, @Valid @RequestBody OrderDTO orderDTO, BindingResult result){
        try{
            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(order);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable @Valid long id){
        return ResponseEntity.ok("Delete order with id = " + id);
    }

}
