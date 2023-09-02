package com.company.storyserver.modal;

import com.company.storyserver.dto.UserDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id",column = @Column(name = "user_id")),
            @AttributeOverride(name="email",column = @Column(name = "user_email")),
    })
    private UserDto user;
    @Column(insertable=false, updatable=false)
    private String image;
    private String caption;
    private LocalDateTime timestamp;

    public Story() {
    }

    public Story(Integer id, UserDto user, String image, String caption, LocalDateTime timestamp) {
        this.id = id;
        this.user = user;
        this.image = image;
        this.caption = caption;
        this.timestamp = timestamp;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Story story)) return false;
        return getId() == story.getId() && Objects.equals(getUser(), story.getUser()) && Objects.equals(getImage(), story.getImage()) && Objects.equals(getCaption(), story.getCaption()) && Objects.equals(getTimestamp(), story.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getImage(), getCaption(), getTimestamp());
    }
}
