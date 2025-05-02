package com.example.Blog_test.controller;

import com.example.Blog_test.payload.ApiResponse;
import com.example.Blog_test.payload.CategoryDto;
import com.example.Blog_test.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody  CategoryDto categoryDto){
      CategoryDto categoryDto1 =  categoryService.createCategory(categoryDto);
      return  new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{cid}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer cid){
        CategoryDto categoryDto1 =  categoryService.updateCategory(categoryDto,cid);
        return  ResponseEntity.ok(categoryDto1);
    }

    @DeleteMapping("/delete/{cid}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer cid){
        categoryService.deleteCategory(cid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("getCategory/{cid}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer cid){
        CategoryDto categoryByID = categoryService.getCategoryByID(cid);
        return ResponseEntity.ok(categoryByID);
    }

    @GetMapping("getAllCategory")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(allCategory);
    }


}
