package com.example.Blog_test.controller;

import com.example.Blog_test.entity.Post;
import com.example.Blog_test.payload.ApiResponse;
import com.example.Blog_test.payload.PostDto;
import com.example.Blog_test.service.PostService;
import jakarta.validation.Valid;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/user/{userId}/category/{categoryId}/addpost")
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
     PostDto postDto1=postService.createPost(postDto,userId,categoryId);
     return  new ResponseEntity<PostDto>(postDto1, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostOfUser(@PathVariable Integer userId){
        List<PostDto> postsByUser = postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostAccCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/allposts")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> allPost = postService.getAllPost();
        return  new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
    }

    @GetMapping("/getpost/{pid}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer pid){
        PostDto postById = postService.getPostById(pid);
        return ResponseEntity.ok(postById);
    }

    @DeleteMapping("/deletePost/{pid}")
    public ApiResponse deletePost(@PathVariable Integer pid){
        postService.deletePost(pid);
        return new ApiResponse("Post deleted Successfully",true);
    }

    @PutMapping("/updatePost/{pid}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer pid){
        PostDto postDto1 = postService.updatePost(postDto, pid);
        return  new ResponseEntity<PostDto>(postDto1,HttpStatus.OK);
    }
}
