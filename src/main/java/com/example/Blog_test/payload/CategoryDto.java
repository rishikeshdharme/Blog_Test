package com.example.Blog_test.payload;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CategoryDto {


    private  Integer categoryId;

    @NotBlank
    @Size(min = 3)
    private String categorytittle;

    @NotBlank
    @Size(min = 10)
    private String categoryDescription;

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategorytittle() {
        return categorytittle;
    }

    public void setCategorytittle(String categorytittle) {
        this.categorytittle = categorytittle;
    }
}
