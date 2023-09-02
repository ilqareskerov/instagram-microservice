package com.company.userservice.dto;

public class RequestPost {
    private Integer id;
    private Integer postId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public RequestPost(Integer id, Integer postId) {
        this.id = id;
        this.postId = postId;
    }
}
