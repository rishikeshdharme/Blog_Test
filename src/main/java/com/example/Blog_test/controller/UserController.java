package com.example.Blog_test.controller;

import com.example.Blog_test.payload.ApiResponse;
import com.example.Blog_test.payload.UserRequest;
import com.example.Blog_test.payload.UserResponse;
import com.example.Blog_test.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){

            UserResponse createduser = userService.createUser(userRequest);
            return  new ResponseEntity<>(createduser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest,@PathVariable("userId") Integer uid){
       UserResponse userResponse = userService.updateUser(userRequest,uid);
       return  ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId){
        return ResponseEntity.ok(userService.findById(userId));
    }


}
