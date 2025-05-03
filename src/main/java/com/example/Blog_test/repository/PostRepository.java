package com.example.Blog_test.repository;

import com.example.Blog_test.entity.Category;
import com.example.Blog_test.entity.Post;
import com.example.Blog_test.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByCategory(Category category,Pageable pageable);
}
