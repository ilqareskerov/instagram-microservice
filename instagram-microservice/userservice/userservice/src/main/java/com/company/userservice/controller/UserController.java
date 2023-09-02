package com.company.userservice.controller;

import com.company.userservice.dto.LoginUserDto;
import com.company.userservice.dto.RequestPost;
import com.company.userservice.modal.User;
import com.company.userservice.response.MessageResponse;
import com.company.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }
    @GetMapping("username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }
    @PutMapping("follow/{followerUserId}")
    public ResponseEntity<MessageResponse> followUser(@PathVariable Integer followerUserId, @RequestHeader("Authorization") String token){
        User user=userService.findUserProfile(token);
        MessageResponse response=new MessageResponse(userService.followUser(user.getId(),followerUserId));
        return ResponseEntity.ok(response);
    }
    @PutMapping("unfollow/{unFollowerUserId}")
    public ResponseEntity<MessageResponse> unFollowUser(@PathVariable long unFollowerUserId,@RequestHeader("Authorization") String token){
        User user=userService.findUserProfile(token);
        MessageResponse response=new MessageResponse(userService.unFollowUser(user.getId(),unFollowerUserId));
        return ResponseEntity.ok(response);
    }
    @GetMapping("req")
    public ResponseEntity<User> findUserProfile(@RequestParam Integer userId){
        User user=userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping("m/{userIds}")
    public ResponseEntity<List<User>> findUserByUserIds(@PathVariable List<Long> userIds){
        List<User> users=userService.findUserByIds(userIds);
        return ResponseEntity.ok(users);
    }
    @GetMapping("search")
    public ResponseEntity<List<User>> searchUser(@RequestParam String query){
        List<User> users=userService.searchUser(query);
        return ResponseEntity.ok(users);
    }
    @PutMapping("account/edit/{userId}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Integer userId,@RequestBody User user){
//        User reqUser = userService.findUserProfile(token);
        User reqUser = userService.findUserById(userId);
        User updatedUser=userService.updateUserDetails(user,reqUser);
        return ResponseEntity.ok(updatedUser);
    }
    @PostMapping("register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userService.registerUser(user));
    }
    @PostMapping("login")
    public ResponseEntity<User> loginUser(@RequestBody LoginUserDto loginUserDto){
        User user = userService.findUserByUsername(loginUserDto.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @PutMapping("account/add/savedpost")
    public ResponseEntity<String> addUserSavedPost(@RequestBody RequestPost user){
        return ResponseEntity.ok(userService.savedPost(user));
    }
    @PutMapping("account/add/unsavedpost")
    public ResponseEntity<String> addUserUnsavedPost(@RequestBody RequestPost user){
        return ResponseEntity.ok(userService.unsavedPost(user));
    }
}
