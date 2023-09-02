package com.company.userservice.service;

import com.company.userservice.dto.RequestPost;
import com.company.userservice.dto.UserDto;
import com.company.userservice.exception.GenericException;
import com.company.userservice.modal.User;
import com.company.userservice.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class UserService {
    private BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public User registerUser(User user){
        if(isUserEmailExists(user.getEmail()))
            throw new GenericException(HttpStatus.BAD_REQUEST,"Email already exists");
        if(isUsernameExists(user.getUsername()))
            throw new GenericException(HttpStatus.BAD_REQUEST,"Username already exists");
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserById(long id){
        return userRepository.findById(id).orElseThrow(()->new GenericException(HttpStatus.NOT_FOUND,"User not found"));
    }
    public User findUserProfile(String token){
//        token=token.substring("Bearer ".length()).trim();
//        JwtTokenClaims claims=jwtTokenProvider.getClaimsFromToken(token);
//         String email=claims.getUsername();
         Optional<User> opt = Optional.of(userRepository.findByUsername(token).get());
         if(opt.isPresent())
             return opt.get();
         else
             throw new GenericException(HttpStatus.NOT_FOUND,"User not found");
    }
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new GenericException(HttpStatus.NOT_FOUND,"User not found"));
    }
    public String followUser(Integer reqUserId,Integer followerUserId){
        User reqUser=findUserById(reqUserId);
        User followerUser=findUserById(followerUserId);
        UserDto follower=new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setImage(reqUser.getImage());
        follower.setName(reqUser.getName());
        follower.setUsername(reqUser.getUsername());
        UserDto following=new UserDto();
        following.setEmail(followerUser.getEmail());
        following.setId(followerUser.getId());
        following.setImage(followerUser.getImage());
        following.setName(followerUser.getName());
        following.setUsername(followerUser.getUsername());
        reqUser.getFollowing().add((int) follower.getId());
        followerUser.getFollower().add((int) following.getId());
        userRepository.save(reqUser);
        userRepository.save(followerUser);

        return "You are following "+followerUser.getUsername()+" now";
    }
    public String unFollowUser(long reqUserId,long followerUserId){
        User reqUser=findUserById(reqUserId);
        User followerUser=findUserById(followerUserId);
        UserDto follower=new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setImage(reqUser.getImage());
        follower.setName(reqUser.getName());
        follower.setUsername(reqUser.getUsername());
        UserDto following=new UserDto();
        following.setEmail(followerUser.getEmail());
        following.setId(followerUser.getId());
        following.setImage(followerUser.getImage());
        following.setName(followerUser.getName());
        following.setUsername(followerUser.getUsername());
        reqUser.getFollowing().remove(follower);
        followerUser.getFollower().remove(following);
        userRepository.save(reqUser);
        userRepository.save(followerUser);
        return "You are not following "+followerUser.getUsername()+" now";
    }
    public List<User> findUserByIds(List<Long> userIds){
        List<User> users=userRepository.findAllUsersByIds(userIds);
        if(users.isEmpty())
            throw new GenericException(HttpStatus.NOT_FOUND,"Users not found");
        return users;
    }
   public List<User> searchUser(String query){
       List<User> users=userRepository.findByQuery(query);
         if(users.isEmpty())
              throw new GenericException(HttpStatus.NOT_FOUND,"Users not found");
        return users;
   }
   public User updateUserDetails(User updatedUser,User existingUser){
        if(updatedUser.getName()!=null)
            existingUser.setName(updatedUser.getName());
        if(updatedUser.getEmail()!=null)
            existingUser.setEmail(updatedUser.getEmail());
        if(updatedUser.getUsername()!=null)
            existingUser.setUsername(updatedUser.getUsername());
        if(updatedUser.getImage()!=null)
            existingUser.setImage(updatedUser.getImage());
        if (updatedUser.getBio()!=null)
            existingUser.setBio(updatedUser.getBio());
        if(updatedUser.getWebsite()!=null)
            existingUser.setWebsite(updatedUser.getWebsite());
        if (updatedUser.getGender()!=null)
            existingUser.setGender(updatedUser.getGender());
        if(updatedUser.getMobile()!=null)
            existingUser.setMobile(updatedUser.getMobile());
        if(updatedUser.getId() == existingUser.getId()){
            IntStream.range(0, updatedUser.getSavedPost().size())
                    .forEach(i -> existingUser.getSavedPost().set(i, updatedUser.getSavedPost().get(i)));
            existingUser.getStories().addAll(updatedUser.getStories());
            return userRepository.save(existingUser);
        }
        throw new GenericException(HttpStatus.BAD_REQUEST,"User id not matched");
   }
   public String savedPost(RequestPost requestPost){
        User user=findUserById(requestPost.getId());
        if (!user.getSavedPost().contains(requestPost.getPostId())) {
            user.getSavedPost().add(requestPost.getPostId());
            userRepository.save(user);
            return "Post saved";
        }
        return "Post already saved";
   }
    public String unsavedPost(RequestPost requestPost){
        User user=findUserById(requestPost.getId());
        if (user.getSavedPost().contains(requestPost.getPostId())) {
            user.getSavedPost().remove(requestPost.getPostId());
            userRepository.save(user);
            return "Post unsaved";
        }
        return "Post already unsaved";
    }
   private boolean isUserEmailExists(String email){

        return userRepository.existsByEmail(email);
   }
   private boolean isUsernameExists(String username){
        return userRepository.existsByUsername(username);
   }
}
