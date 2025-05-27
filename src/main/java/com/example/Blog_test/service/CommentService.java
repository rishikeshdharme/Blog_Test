package com.example.Blog_test.service;

import com.example.Blog_test.entity.Comment;
import com.example.Blog_test.entity.Post;
import com.example.Blog_test.entity.User;
import com.example.Blog_test.exception.ResourceNotFoundException;
import com.example.Blog_test.payload.CommentDto;
import com.example.Blog_test.repository.CommentRepository;
import com.example.Blog_test.repository.PostRepository;
import com.example.Blog_test.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentDto addComment(CommentDto commentDto,Integer postId,Integer userId){
       User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","user id",userId));
       Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","post id",postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
        CommentDto commentDto1 = modelMapper.map(comment, CommentDto.class);
        commentDto1.setUsername(comment.getUser().getName());
        return commentDto1;
    }

    public void deleteComment(Integer commentId){
       Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","comment id",commentId));
       commentRepository.deleteById(comment.getCid());
    }
}
