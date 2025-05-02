package com.example.Blog_test.service;

import com.example.Blog_test.entity.Category;
import com.example.Blog_test.exception.ResourceNotFoundException;
import com.example.Blog_test.payload.ApiResponse;
import com.example.Blog_test.payload.CategoryDto;
import com.example.Blog_test.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public CategoryDto createCategory(CategoryDto categoryDto){
      Category category =  modelMapper.map(categoryDto,Category.class);
        categoryRepository.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }


    public CategoryDto updateCategory(CategoryDto categoryDto,Integer cid){
       Category category = categoryRepository.findById(cid).orElseThrow(()-> new ResourceNotFoundException("Category","Id",cid));
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategorytittle(categoryDto.getCategorytittle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        categoryRepository.save(category);
        return  modelMapper.map(category,CategoryDto.class);
    }

    public void deleteCategory(Integer cid){
        Category category = categoryRepository.findById(cid).orElseThrow(()-> new ResourceNotFoundException("Category","Id",cid));
        categoryRepository.deleteById(category.getCategoryId());
    }

    public CategoryDto getCategoryByID(Integer cid){
        Category category = categoryRepository.findById(cid).orElseThrow(()-> new ResourceNotFoundException("Category","Id",cid));
        CategoryDto categoryDto = modelMapper.map(category,CategoryDto.class);
        return categoryDto;
    }

    public List<CategoryDto> getAllCategory(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> collect = categoryList.stream().map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return  collect;
    }
}
