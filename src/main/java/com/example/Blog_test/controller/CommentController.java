package com.example.Blog_test.controller;

import com.example.Blog_test.payload.ApiResponse;
import com.example.Blog_test.payload.CommentDto;
import com.example.Blog_test.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

        @PostMapping("/addComments/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDto> addComments(@RequestBody CommentDto commentDto, @PathVariable Integer postId, @PathVariable Integer userId){
      CommentDto commentDto1 =   commentService.addComment(commentDto,postId,userId);
        return  new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteComments/{cid}")
    public ResponseEntity<ApiResponse> deleteComments(@PathVariable Integer cid){
        commentService.deleteComment(cid);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Successfully",true),HttpStatus.OK);
    }
}
