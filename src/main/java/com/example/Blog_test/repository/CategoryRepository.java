package com.example.Blog_test.repository;

import com.example.Blog_test.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
