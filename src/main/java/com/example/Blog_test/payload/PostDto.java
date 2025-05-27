package com.example.Blog_test.payload;

import com.example.Blog_test.entity.Category;
import com.example.Blog_test.entity.Comment;
import com.example.Blog_test.entity.User;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PostDto {

    private Integer postId;
    @NotBlank
    private String tittle;
    @NotBlank
    private String content;
    private Date postDate;
    private String imageName;
    private CategoryDto category;
    private UserResponse user;
    private Set<CommentDto> comment = new HashSet<>();

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }



    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public Set<CommentDto> getComment() {
        return comment;
    }

    public void setComment(Set<CommentDto> comment) {
        this.comment = comment;
    }
}
