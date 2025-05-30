package com.example.shopapp.Controllers;

import com.example.shopapp.Dtos.UserDTO;
import com.example.shopapp.Dtos.UserLoginDTO;
import com.example.shopapp.Service.IUserService;
import com.example.shopapp.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.BeanInfo;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/${api.prefix}/users")
public class UserController {
    private final IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO , BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password and retype password must be the same");
            }
            userService.createUser(userDTO);
            return ResponseEntity.ok("Register User successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO ){
        try{
           String token = userService.login(userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword());
           return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
