package com.company.commentserver.modal;

import com.company.commentserver.dto.UserDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id",column = @Column(name = "user_id")),
            @AttributeOverride(name="email",column = @Column(name = "user_email")),
    })
    private UserDto user;
    private String content;
    Set<Integer> likedByUsers=new HashSet<>();
    private LocalDateTime created_at;

    public Comment() {
    }

    public Comment(Integer id, UserDto user, String content, Set<Integer> likedByUsers, LocalDateTime created_at) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.likedByUsers = likedByUsers;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Integer> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<Integer> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
