package com.company.commentserver.controller;

import com.company.commentserver.dto.CommentDto;
import com.company.commentserver.modal.Comment;
import com.company.commentserver.modal.User;
import com.company.commentserver.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment/")
public class CommentController {
    private final CommentService commentService;
    private final UserFeign userService;
    private final PostFeign postService;

    public CommentController(CommentService commentService, UserFeign userService, PostFeign postService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("create/{postId}/{userId}")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto comment, @PathVariable Integer userId, @PathVariable Integer postId){
        User user=userService.findUserById(userId).getBody();
        Comment createdComment=commentService.createComment(comment,postId,user.getId());
        return ResponseEntity.ok(createdComment);
    }
    @PutMapping("like/{commentId}")
    public ResponseEntity<Comment> likeComment(@PathVariable("commentId") Integer commentId,@RequestHeader("Authorization") String  token){
        User user=userService.findUserProfile(token).getBody();
        Comment comment=commentService.likeComment(commentId,user.getId());
        return ResponseEntity.ok(comment);
    }
    @PutMapping("unlike/{commentId}")
    public ResponseEntity<Comment> unlikeComment(@PathVariable("commentId") Integer commentId,@RequestHeader("Authorization") String  token){
        User user=userService.findUserProfile(token).getBody();
        Comment comment=commentService.unlikeComment(commentId,user.getId());
        return ResponseEntity.ok(comment);
    }
    @GetMapping("hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }
}
