package com.company.postserver.controller;

import com.company.postserver.modal.Post;
import com.company.postserver.modal.User;
import com.company.postserver.response.MessageResponse;
import com.company.postserver.service.PostService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post/")
public class PostController {
    private final PostService postService;
    private final UserFeign userService;

    public PostController(PostService postService, UserFeign userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("create/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable("userId") Integer userId){
        User user=userService.findUserById(userId).getBody();
        Post createdPost=postService.createPost(post,user.getId());
        return ResponseEntity.ok(createdPost);
    }
    @GetMapping("all/{id}")
    public ResponseEntity<List<Post>> findPostsByUserId(@PathVariable("id") Integer userId){
        List<Post> posts=postService.findPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("following/{ids}")
    public ResponseEntity<List<Post>> findAllPostByUserIds(@PathVariable("ids") List<Long> userId){
        List<Post> posts=postService.findAllPostByUserIds(userId);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable("postId") Integer postId){
        Post post=postService.findPostById(postId);
        return ResponseEntity.ok(post);
    }
    @PutMapping("like/{postId}/{userId}")
    public ResponseEntity<Post> likePost(@PathVariable("postId") Integer postId,@PathVariable("userId") Integer userId){
        User user=userService.findUserById(userId).getBody();
        Post post=postService.likePost(postId,user.getId());
        return ResponseEntity.ok(post);
    }
    @PutMapping("unlike/{postId}/{userId}")
    public ResponseEntity<Post> unLikePost(@PathVariable("postId") Integer postId,@PathVariable("userId") Integer userId){
        User user=userService.findUserById(userId).getBody();
        Post post=postService.unLikePost(postId,user.getId());
        return ResponseEntity.ok(post);
    }
    @DeleteMapping("delete/{postId}/{userId}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") Integer postId,@PathVariable("userId") Integer userId){
        User user=userService.findUserById(userId).getBody();
       String message= postService.deletePost(postId,user.getId());
        return ResponseEntity.ok(new MessageResponse(message));
    }
    @PutMapping("saved/{postId}/{userId}")
    public ResponseEntity<String> savedPost(@PathVariable("postId") Integer postId,@PathVariable("userId") Integer userId){
        User user=userService.findUserById(userId).getBody();
        String message=postService.savedPost(postId,user.getId());
        MessageResponse messageResponse=new MessageResponse(message);
        return ResponseEntity.ok(message);
    }

    @PutMapping("unsaved/{postId}/{userId}")
    public ResponseEntity<String> unSavedPost(@PathVariable("postId") Integer postId,@PathVariable("userId") Integer userId){
        User user=userService.findUserById(userId).getBody();
        String message=postService.unSavedPost(postId,user.getId());
        return ResponseEntity.ok(message);
    }
    @PutMapping("update/{postId}/{userId}")
    public ResponseEntity<Post> updatePost(@PathVariable("postId") Integer postId,@RequestBody Post post,@PathVariable("userId") Integer userId){
        User user=userService.findUserById(userId).getBody();
        Post updatedPost=postService.updatePost(postId,post);
        return ResponseEntity.ok(updatedPost);
    }

}
