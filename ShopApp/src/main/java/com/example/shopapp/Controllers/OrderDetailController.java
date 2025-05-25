package com.example.shopapp.Controllers;

import com.example.shopapp.Dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.prefix}/order_details")
public class OrderDetailController {

    @PostMapping("/add")
    public ResponseEntity<?> addOrderDetail( @Valid @RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok("Add Order Detail");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAllOrderDetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("Order Details with id = " + id );
    }

    //lay ra danh sach order detail cua order nao do
    @GetMapping("/order/{orderID}")
    public ResponseEntity<?> getAllOrderDetailByOrderID(@Valid @PathVariable("orderID") Long orderID){
        return ResponseEntity.ok("All Order Details by Order ID" + orderID);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id, @Valid @RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok("Update Order Detail with id = " + id + " and OrderDetailDTO = " + orderDetailDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("Delete Order Detail with id = " + id);
    }
}
