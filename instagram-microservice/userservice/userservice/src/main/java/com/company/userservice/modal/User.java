package com.company.userservice.modal;

import com.company.userservice.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String mobile;
    private String website;
    private String bio;
    private String gender;
    private String image;
    private String password;
    private Set<Integer> follower= new HashSet<>();
    @JsonIgnore
    private Set<Integer> following= new HashSet<>();
    @JsonIgnore
    private List<Integer> stories=new ArrayList<Integer>();
    @JsonIgnore
    private List<Integer> savedPost=new ArrayList<Integer>();

    public User() {
    }

    public User(Integer id,
                String username,
                String name,
                String email,
                String mobile,
                String website,
                String bio,
                String gender,
                String image,
                String password,
                Set<Integer> follower,
                Set<Integer> following,
                List<Integer> stories,
                List<Integer> savedPost) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.website = website;
        this.bio = bio;
        this.gender = gender;
        this.image = image;
        this.password = password;
        this.follower = follower;
        this.following = following;
        this.stories = stories;
        this.savedPost = savedPost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Integer> getFollower() {
        return follower;
    }

    public void setFollower(Set<Integer> follower) {
        this.follower = follower;
    }

    public Set<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Integer> following) {
        this.following = following;
    }

    public List<Integer> getStories() {
        return stories;
    }

    public void setStories(List<Integer> stories) {
        this.stories = stories;
    }

    public List<Integer> getSavedPost() {
        return savedPost;
    }

    public void setSavedPost(List<Integer> savedPost) {
        this.savedPost = savedPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId() == user.getId() && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getName(), user.getName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getMobile(), user.getMobile()) && Objects.equals(getWebsite(), user.getWebsite()) && Objects.equals(getBio(), user.getBio()) && Objects.equals(getGender(), user.getGender()) && Objects.equals(getImage(), user.getImage()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getFollower(), user.getFollower()) && Objects.equals(getFollowing(), user.getFollowing()) && Objects.equals(getStories(), user.getStories()) && Objects.equals(getSavedPost(), user.getSavedPost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getName(), getEmail(), getMobile(), getWebsite(), getBio(), getGender(), getImage(), getPassword(), getFollower(), getFollowing(), getStories(), getSavedPost());
    }
}