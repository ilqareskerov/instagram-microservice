package com.company.commentserver.service;

import com.company.commentserver.controller.PostFeign;
import com.company.commentserver.controller.UserFeign;
import com.company.commentserver.dto.CommentDto;
import com.company.commentserver.dto.UserDto;
import com.company.commentserver.exception.GenericException;
import com.company.commentserver.modal.Comment;
import com.company.commentserver.modal.Post;
import com.company.commentserver.modal.User;
import com.company.commentserver.repo.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository repository;
     private final UserFeign userService;
     private final PostFeign postService;

    public CommentService(CommentRepository repository, UserFeign userService, PostFeign postService) {
        this.repository = repository;
        this.userService = userService;
        this.postService = postService;
    }

    public Comment createComment(CommentDto comment, Integer postId, Integer userId){
        User user=userService.findUserById(userId).getBody();
        Post post=postService.findPostById(postId).getBody();
        Comment comment1=new Comment();
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setImage(user.getImage());
        userDto.setName(user.getName());
        comment1.setContent(comment.getComment());
        comment1.setUser(userDto);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime truncatedDateTime = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        comment1.setCreated_at(truncatedDateTime);
        Comment createdComment =repository.save(comment1);
        post.getComments().add(createdComment.getId());
        postService.updatePost(postId,post,userId);
        return createdComment;
    }
    public Comment findCommentById(Integer commentId){
        Optional<Comment> opt=repository.findById(commentId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new GenericException("Comment not found");
    }
    public Comment likeComment(Integer commentId, Integer userId){
        User user=userService.findUserById(userId).getBody();
        Comment comment=findCommentById(commentId);
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setImage(user.getImage());
        userDto.setName(user.getName());
        comment.getLikedByUsers().add(userDto.getId());

        return repository.save(comment);
    }
    public Comment unlikeComment(Integer commentId, Integer userId){
        User user=userService.findUserById(userId).getBody();
        Comment comment=findCommentById(commentId);
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setImage(user.getImage());
        userDto.setName(user.getName());
        comment.getLikedByUsers().remove(userDto);

        return repository.save(comment);
    }



}
