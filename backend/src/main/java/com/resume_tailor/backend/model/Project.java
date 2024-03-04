package com.resume_tailor.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "projects")
public class Project {
    @Id
    private String id;

    @NotNull(message = "User ID cannot be null!")
    private String userId;

    @NotBlank(message = "Project name can not be null!")
    private String name;

    private String link;

    @NotBlank(message = "Project description can not be null")
    private String description ;

    //Getters and Setters
    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getLink(){
        return this.link;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public String toString(){
        return "Project [id=" + id + ", name=" + name + ", desc=" + description + ", link =" + link + "]";
    }
}
