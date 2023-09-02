package com.company.postserver.service;

import com.company.postserver.controller.UserFeign;
import com.company.postserver.dto.RequestPost;
import com.company.postserver.dto.UserDto;
import com.company.postserver.exception.GenericException;
import com.company.postserver.modal.Post;
import com.company.postserver.modal.User;
import com.company.postserver.repo.PostRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserFeign userService;

    public PostService(PostRepository postRepository, UserFeign userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post createPost(Post post, Integer userId){
        User user=userService.findUserById(userId).getBody();
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setImage(user.getImage());
        userDto.setName(user.getName());
        post.setUser(userDto);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime truncatedDateTime = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        post.setCreated_at(truncatedDateTime);
        Post createdPost =postRepository.save(post);
        return createdPost;
    }
    public String deletePost(Integer postId, Integer userId){
        Post post=findPostById(postId);
        User user=userService.findUserById(userId).getBody();
      if(post.getUser().getId()==user.getId()){
          postRepository.delete(post);
          return "Post deleted successfully";
      }
         throw new GenericException(HttpStatus.BAD_REQUEST,"You can't delete this post");
    }
    public List<Post> findPostsByUserId(long userId){
        List<Post> posts=postRepository.findByUserId(userId);
        if(posts.size()==0){
            throw new GenericException(HttpStatus.NOT_FOUND,"No posts found");
        }
        return posts;
    }
    public Post findPostById(Integer postId){
        Optional<Post> opt=postRepository.findById(postId);
        if(opt.isPresent())
            return opt.get();
        else
            throw new GenericException(HttpStatus.NOT_FOUND,"Post not found");
    }

    public List<Post> findAllPostByUserIds(List<Long> userIds){
        List<Post> posts=postRepository.findAllPostByUserIds(userIds);
        if (posts.size()==0)
            throw new GenericException(HttpStatus.NOT_FOUND,"No posts found");
        return posts;
    }

    public String savedPost(Integer postId, Integer userId){
        Post post=findPostById(postId);
        User user=userService.findUserById(userId).getBody();
        RequestPost requestPost=new RequestPost(userId,post.getId());
        String message = userService.addUserSavedPost(requestPost).getBody();
        return message;

    }
    public String unSavedPost(Integer postId,Integer userId){
        Post post=findPostById(postId);
        User user=userService.findUserById(userId).getBody();
        RequestPost requestPost=new RequestPost(user.getId(),post.getId());
        String message = userService.addUserUnsavedPost(requestPost).getBody();
        return message;
    }
    public Post likePost(Integer postId,Integer userId){
        Post post=findPostById(postId);
        User user=userService.findUserById(userId).getBody();
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setImage(user.getImage());
        userDto.setName(user.getName());
        post.getLikedByUsers().add(userDto.getId());
        return postRepository.save(post);
    }
    public Post unLikePost(Integer postId,Integer userId){
        Post post=findPostById(postId);
        User user=userService.findUserById(userId).getBody();
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setImage(user.getImage());
        userDto.setName(user.getName());
        post.getLikedByUsers().remove(userDto.getId());
        return postRepository.save(post);
    }
    public Post updatePost(Integer postId,Post post){
        Post post1=findPostById(postId);
        post1.setComments(post.getComments());
        return postRepository.save(post1);
    }


}
