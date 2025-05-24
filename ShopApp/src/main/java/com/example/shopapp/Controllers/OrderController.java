package com.example.shopapp.Controllers;

import com.example.shopapp.Dtos.OrderDTO;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/${api.prefix}/orders")
public class OrderController {
    @PostMapping("/adds")
    public ResponseEntity<?> register(@Valid @RequestBody OrderDTO orderDTO , BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }
            return ResponseEntity.ok("Create order successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable("id") long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body("Order with id = " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable long id, @Valid @RequestBody OrderDTO orderDTO, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }
            return ResponseEntity.ok("Update order successfully");
        }catch (Exception e){}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update order failed");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable @Valid long id){
        return ResponseEntity.ok("Delete order with id = " + id);
    }
}
