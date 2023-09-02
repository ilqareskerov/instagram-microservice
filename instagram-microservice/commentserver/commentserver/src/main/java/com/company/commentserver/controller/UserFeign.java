package com.company.commentserver.controller;

import com.company.commentserver.modal.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service",path = "/api/v1/user/")
public interface UserFeign {
     @GetMapping("id/{userId}")
     ResponseEntity<User> findUserById(@PathVariable("userId") Integer userId);
     @PutMapping("account/edit/{userId}")
     ResponseEntity<User> updateUserProfile(@PathVariable Integer userId, @RequestBody User user);
     @GetMapping("req")
     ResponseEntity<User> findUserProfile(@RequestHeader("Authorization") String token);
}

