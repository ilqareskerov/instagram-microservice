package com.company.commentserver.controller;

import com.company.commentserver.modal.Post;
import com.company.commentserver.response.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "post-service",path = "/api/v1/post/")
public interface PostFeign {
    @PostMapping("create")
     ResponseEntity<Post> createPost(@RequestBody Post post, @RequestHeader("Authorization") String  token);
    @GetMapping("all/{id}")
     ResponseEntity<List<Post>> findPostsByUserId(@PathVariable("id") long userId);

    @GetMapping("following/{ids}")
     ResponseEntity<List<Post>> findAllPostByUserIds(@PathVariable("ids") List<Long> userId);
    @GetMapping("{postId}")
     ResponseEntity<Post> findPostById(@PathVariable("postId") long postId);
    @PutMapping("like/{postId}")
     ResponseEntity<Post> likePost(@PathVariable("postId") Integer postId,@RequestHeader("Authorization") String  token);
    @PutMapping("unlike/{postId}")
     ResponseEntity<Post> unLikePost(@PathVariable("postId") Integer postId,@RequestHeader("Authorization") String  token);
    @DeleteMapping("delete/{postId}")
     ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String  token);
    @PutMapping("saved/{postId}")
     ResponseEntity<MessageResponse> savedPost(@PathVariable("postId") Integer postId,@RequestHeader("Authorization") String  token);

    @PutMapping("unsaved/{postId}")
     ResponseEntity<MessageResponse> unSavedPost(@PathVariable("postId") Integer postId,@RequestHeader("Authorization") String  token);
    @PutMapping("update/{postId}/{userId}")
     ResponseEntity<Post> updatePost(@PathVariable("postId") Integer postId,@RequestBody Post post,@PathVariable("userId") Integer userId);
}
