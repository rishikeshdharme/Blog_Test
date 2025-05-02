package com.example.Blog_test.repository;

import com.example.Blog_test.entity.Category;
import com.example.Blog_test.entity.Post;
import com.example.Blog_test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
}
