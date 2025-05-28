package com.example.shopapp.Controllers;

import com.example.shopapp.Dtos.CategoryDTO;
import com.example.shopapp.Model.Category;
import com.example.shopapp.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${api.prefix}/categories")
//@Validated
public class CategoryController {
    private final CategoryService categoryService;
    // hien thi tat ca cac category
    @PostMapping()
    // neu tham so truyen vao la 1 object thi sao la 1 request => Data Transfer Object = Request Object
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errors.toString());
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Add Category" + categoryDTO);
    }
    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam("Page") int page, @RequestParam("Size") int size){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable long id, @Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update Category with id = " + id + " successfully" );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Delete Category with id = " + id + " successfully");
    }
}
