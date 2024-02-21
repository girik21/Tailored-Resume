package com.resume_tailor.backend.model;

import java.time.LocalDateTime;

public class CoverLetter {
    private Integer id; // Primary Key

    private Integer userId;
    private String link;
    private LocalDateTime createdAt;
    private boolean active;
    private String appliedJobLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAppliedJobLink() {
        return appliedJobLink;
    }

    public void setAppliedJobLink(String appliedJobLink) {
        this.appliedJobLink = appliedJobLink;
    }
}
