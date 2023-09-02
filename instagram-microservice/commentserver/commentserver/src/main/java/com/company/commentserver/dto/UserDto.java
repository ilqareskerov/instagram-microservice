package com.company.commentserver.dto;

import java.util.Objects;

public class UserDto {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String image;

    public UserDto(Integer id, String username, String name, String email, String image) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public UserDto() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto userDto)) return false;
        return getId() == userDto.getId() && Objects.equals(getUsername(), userDto.getUsername()) && Objects.equals(getName(), userDto.getName()) && Objects.equals(getEmail(), userDto.getEmail()) && Objects.equals(getImage(), userDto.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getName(), getEmail(), getImage());
    }
}
