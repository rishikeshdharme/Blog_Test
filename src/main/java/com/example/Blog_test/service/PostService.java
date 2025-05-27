package com.example.Blog_test.service;

import com.example.Blog_test.entity.Category;
import com.example.Blog_test.entity.Comment;
import com.example.Blog_test.entity.Post;
import com.example.Blog_test.entity.User;
import com.example.Blog_test.exception.ResourceNotFoundException;
import com.example.Blog_test.payload.PostDto;
import com.example.Blog_test.payload.PostResponse;
import com.example.Blog_test.repository.CategoryRepository;
import com.example.Blog_test.repository.PostRepository;
import com.example.Blog_test.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    postDto.getComment().forEach(c ->{
                Comment comment = post.getCommentSet().stream().filter(cm ->cm.getCid().equals(c.getCid())).findFirst().orElse(null);
                if(comment!=null){
                    c.setUsername(comment.getUser().getName());
                }
            }
            );
    return postDto;
    }

    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDirection){
        //Add Pagination ---
        Sort sort = null;
        if(sortDirection.equalsIgnoreCase("asc")){
           sort = Sort.by(sortBy).ascending();
        }
        else{
           sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagingAll = postRepository.findAll(pageable);
        List<Post> posts = pagingAll.getContent();

        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagingAll.getNumber());
        postResponse.setPageSize(pagingAll.getSize());
        postResponse.setTotalElement(pagingAll.getTotalElements());
        postResponse.setTotalPage(pagingAll.getTotalPages());
        postResponse.setLastPage(pagingAll.isLast());

        return postResponse;
    }

    //get all posts by user
    public PostResponse  getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize){
     User user =   userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserId",userId));

     Pageable pageable = PageRequest.of(pageNumber,pageSize);
     Page<Post> posts = postRepository.findByUser(user,pageable);
     List<PostDto> postDtos =  posts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
     PostResponse postResponse = new PostResponse();
     postResponse.setContent(postDtos);
     postResponse.setPageNumber(posts.getNumber());
     postResponse.setPageSize(posts.getSize());
     postResponse.setTotalElement(posts.getTotalElements());
     postResponse.setTotalPage(posts.getTotalPages());
     postResponse.setLastPage(posts.isLast());

      return postResponse;
    }

    //get all posts by category
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Post> posts = postRepository.findByCategory(category,pageable);
        List<PostDto> postDtos =  posts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());

        return postResponse;
    }

    public List<PostDto> searchPostsByKeyword(String keyword){
        List<Post> posts = postRepository.searchPostByKeyword("%"+keyword+"%");
        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
