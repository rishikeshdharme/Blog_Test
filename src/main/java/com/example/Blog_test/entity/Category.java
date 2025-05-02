package com.example.Blog_test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer categoryId;

    @Column(name="tittle",nullable = false)
    private String categorytittle;

    @Column(name="description",nullable = false)
    private String categoryDescription;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public Category(String categoryDescription, Integer categoryId, String categorytittle) {
        this.categoryDescription = categoryDescription;
        this.categoryId = categoryId;
        this.categorytittle = categorytittle;
    }

    public Category() {
    }

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
