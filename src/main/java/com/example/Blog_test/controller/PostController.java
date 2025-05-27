package com.example.Blog_test.controller;

import com.example.Blog_test.config.AppConstant;
import com.example.Blog_test.entity.Post;
import com.example.Blog_test.payload.ApiResponse;
import com.example.Blog_test.payload.PostDto;
import com.example.Blog_test.payload.PostResponse;
import com.example.Blog_test.service.FileService;
import com.example.Blog_test.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;

    }

    @PostMapping("/user/{userId}/category/{categoryId}/addpost")
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
     PostDto postDto1=postService.createPost(postDto,userId,categoryId);
     return  new ResponseEntity<PostDto>(postDto1, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostOfUser(@PathVariable Integer userId,
                                                       @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize){
       PostResponse posts = postService.getPostsByUser(userId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostAccCategory(@PathVariable Integer categoryId,
                                                           @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize){
        PostResponse posts = postService.getPostsByCategory(categoryId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }

    @GetMapping("/allposts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false) String postBy,
            @RequestParam(value = "sortDirection",defaultValue = AppConstant.SORT_DIRECTION,required = false) String postDirection
    ){
       PostResponse  postResponse = postService.getAllPost(pageNumber,pageSize,postBy,postDirection);
        return  new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
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

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByKeyword(@PathVariable("keyword") String keyword){
        List<PostDto> postDtos = postService.searchPostsByKeyword(keyword);
        return  new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @PostMapping("/image/upload/{postid}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image")MultipartFile image, @PathVariable Integer postid) throws IOException {
        PostDto postDto = postService.getPostById(postid);
        String filename = fileService.uploadImage(path, image);
        postDto.setImageName(filename);
        PostDto postDto1 = postService.updatePost(postDto, postid);
        return  new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE )
    public void downloadImage(
            @PathVariable String imageName,
            HttpServletResponse httpServletResponse
    ) throws IOException {

        InputStream inputStream = fileService.getResources(path,imageName);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,httpServletResponse.getOutputStream());
    }



}
