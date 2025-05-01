package com.example.Blog_test.service;

import com.example.Blog_test.entity.User;
import com.example.Blog_test.exception.ResourceNotFoundException;
import com.example.Blog_test.payload.UserRequest;
import com.example.Blog_test.payload.UserResponse;
import com.example.Blog_test.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponse createUser(UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setAbout(userRequest.getAbout());
        userRepository.save(user);
//        UserResponse userResponse = new UserResponse();
//        userResponse.setId(user.getId());
//        userResponse.setName(user.getName());
//        userResponse.setPasssword(user.getPassword());
//        userResponse.setEmail(user.getEmail());
//        userResponse.setDecription(user.getAbout());
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }

    public UserResponse updateUser(UserRequest userRequest,Integer id){

        User user = userRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("User","Id",id));
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setAbout(userRequest.getAbout());
//        userRepository.save(user);
//        UserResponse userResponse = new UserResponse();
//        userResponse.setId(user.getId());
//        userResponse.setName(user.getName());
//        userResponse.setPasssword(user.getPassword());
//        userResponse.setEmail(user.getEmail());
//        userResponse.setDecription(user.getAbout());
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }

    public void deleteUser(int id){
      User user =  userRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("User","Id",id));
        userRepository.deleteById(user.getId());
    }

    public UserResponse findById(int id){

           User user= userRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("User","Id",id));
//           UserResponse userResponse = new UserResponse();
//               userResponse.setId(user.getId());
//               userResponse.setName(user.getName());
//               userResponse.setEmail(user.getEmail());
//               userResponse.setPasssword(user.getPassword());
//               userResponse.setDecription(user.getAbout());

        UserResponse userResponse = modelMapper.map(user, UserResponse.class);


        return userResponse;
    }

    public List<UserResponse> getAllUser()
    {

       List<User> users =  userRepository.findAll();
       List<UserResponse> userResponses = users.stream().map(user -> this.convertUsertoUserResponse(user)).collect(Collectors.toList());
        return userResponses;
    }

    public UserResponse convertUsertoUserResponse(User user){
        //use modelMapper to convert one model to another model ---
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);

//        userResponse.setId(user.getId());
//        userResponse.setName(user.getName());
//        userResponse.setEmail(user.getEmail());
//        userResponse.setPasssword(user.getPassword());
//        userResponse.setDecription(user.getAbout());
        return  userResponse;
    }
}
