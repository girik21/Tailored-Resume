package com.resume_tailor.backend.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "resumes")
public class Resume {
    @Id
    private String id;

    @NotNull(message = "User ID cannot be null!")
    private String userId;

    @NotBlank(message = "Link cannot be null!")
    private String link;

    @CreatedDate
    private Date createdAt;

    @NotNull
    private Boolean active;

    private String appliedJobLink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAppliedJobLink() {
        return appliedJobLink;
    }

    public void setAppliedJobLink(String appliedJobLink) {
        this.appliedJobLink = appliedJobLink;
    }
}
