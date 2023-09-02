package com.company.storyserver.controller;
import com.company.storyserver.modal.Story;
import com.company.storyserver.modal.User;
import com.company.storyserver.service.StoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/story")
public class StoryController {
    private final StoryService storyService;
    private final UserFeign userFeign;

    public StoryController(StoryService storyService, UserFeign userFeign) {
        this.storyService = storyService;
        this.userFeign = userFeign;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Story> createStory(@PathVariable("userId") Integer userId,@RequestBody Story story){
//        User user = userFeign.findUserProfile(token).getBody();
        User user = userFeign.findUserById(userId).getBody();
        return ResponseEntity.ok(storyService.createStory(story, user.getId()));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findAllStoryByUserId(@PathVariable("userId") Integer userId){
        User user = userFeign.findUserById(userId).getBody();
        return ResponseEntity.ok(storyService.findStoryByUserId(user.getId()));
    }
}
