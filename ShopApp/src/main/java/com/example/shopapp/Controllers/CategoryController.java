package com.example.shopapp.Controllers;

import com.example.shopapp.Dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
//@Validated
public class CategoryController {
    // hien thi tat ca cac category
    @GetMapping("/all")
    public ResponseEntity<String> getAllCategory(@RequestParam("Page") int page, @RequestParam("Size") int size){
        return ResponseEntity.ok("All Categories page " + page + " size " + size);
    }
    @PostMapping("/add")
    // neu tham so truyen vao la 1 object thi sao la 1 request => Data Transfer Object = Request Object
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO category, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errors.toString());
        }
        return ResponseEntity.ok("Add Category" + category);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable long id){
        return ResponseEntity.ok("Update Category with id = " + id );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        return ResponseEntity.ok("Delete Category with id = " + id);
    }
}
