package com.example.Blog_test.service;

import com.example.Blog_test.entity.Category;
import com.example.Blog_test.entity.Post;
import com.example.Blog_test.entity.User;
import com.example.Blog_test.exception.ResourceNotFoundException;
import com.example.Blog_test.payload.PostDto;
import com.example.Blog_test.repository.CategoryRepository;
import com.example.Blog_test.repository.PostRepository;
import com.example.Blog_test.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId){

      User user =  userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User Id",userId));
      Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
      Post post = modelMapper.map(postDto,Post.class);
      post.setPostDate(new Date());
      post.setImageName("default.png");
      post.setUser(user);
      post.setCategory(category);
      postRepository.save(post);

        PostDto postDto1 = modelMapper.map(post,PostDto.class);
        return postDto1;
    }

    public PostDto updatePost(PostDto postDto,Integer pid){
        Post post =   postRepository.findById(pid).orElseThrow(()-> new ResourceNotFoundException("post","post id",pid));
        post.setTittle(postDto.getTittle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = postRepository.save(post);
        return  modelMapper.map(updatedPost,PostDto.class);
    }

    public void deletePost(Integer pid){
        Post post =   postRepository.findById(pid).orElseThrow(()-> new ResourceNotFoundException("post","post id",pid));
        postRepository.deleteById(pid);
    }

    public PostDto getPostById(Integer pid){
     Post post =   postRepository.findById(pid).orElseThrow(()-> new ResourceNotFoundException("post","post id",pid));
    PostDto postDto =  modelMapper.map(post,PostDto.class);
    return postDto;
    }

    public List<PostDto> getAllPost(){
         List<Post> posts= postRepository.findAll();
        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    //get all posts by user
    public List<PostDto>  getPostsByUser(Integer userId){
     User user =   userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserId",userId));
     List<Post> posts = postRepository.findByUser(user);
     List<PostDto> postDtos =  posts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
      return postDtos;
    }

    //get all posts by category
    public List<PostDto> getPostsByCategory(Integer categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        List<Post> posts = postRepository.findByCategory(category);
        List<PostDto> postDtos =  posts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;

    }
}
