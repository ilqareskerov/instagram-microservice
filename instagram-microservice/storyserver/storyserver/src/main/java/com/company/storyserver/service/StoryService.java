package com.company.storyserver.service;

import com.company.storyserver.controller.UserFeign;
import com.company.storyserver.dto.UserDto;
import com.company.storyserver.exception.exception.GenericException;
import com.company.storyserver.modal.Story;
import com.company.storyserver.modal.User;
import com.company.storyserver.repo.StoryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class StoryService {
    private final StoryRepository storyRepository;
    private final UserFeign userService;

    public StoryService(StoryRepository storyRepository, UserFeign userService) {
        this.storyRepository = storyRepository;
        this.userService = userService;
    }


    public Story createStory(Story story, Integer userId){
        User user = userService.findUserById(userId).getBody();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setImage(user.getImage());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        story.setUser(userDto);
        story.setTimestamp(LocalDateTime.now());
        Story story1=storyRepository.save(story);
        user.getStories().add(story1.getId());
        User user2= userService.updateUserProfile(userId,user).getBody();
//        userRepository.save(user);
        return story1;
    }
    public List<Story> findStoryByUserId(Integer userId){
        User user = userService.findUserById(userId).getBody();
        List<Story> stories =storyRepository.findAllStoryByUserId(user.getId());

        if (stories.size()==0){
            throw new GenericException("No stories found");
        }
        return stories;
    }

}
