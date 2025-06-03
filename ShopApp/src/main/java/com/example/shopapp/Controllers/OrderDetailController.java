package com.example.shopapp.Controllers;

import com.example.shopapp.Dtos.OrderDetailDTO;
import com.example.shopapp.Model.OrderDetail;
import com.example.shopapp.Service.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${api.prefix}/order_details")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    @PostMapping("/add")
    public ResponseEntity<?> addOrderDetail( @Valid @RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws Exception {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok(orderDetail );
    }

    //lay ra danh sach order detail cua order nao do
    @GetMapping("/order/{orderID}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderID") Long orderID){
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderID);
        return ResponseEntity.ok(orderDetails);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id, @Valid @RequestBody OrderDetailDTO orderDetailDTO){
        try{
            OrderDetail orderDetail =  orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok(orderDetail);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        orderDetailService.deleteById(id);
        return ResponseEntity.ok("Delete Order Detail with id = " + id);
    }
}
