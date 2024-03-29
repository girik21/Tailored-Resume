package com.resume_tailor.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "projects")
public class Project {
    @Id
    private String id;

    @NotBlank(message = "Project name can not be null!")
    private String name;

    @NotNull(message = "Start date cannot be null.")
    private Date startDate;

    private Date endDate;

    @NotBlank(message = "Employer cannot be null or empty.")
    private String employer;

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

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getLink(){
        return this.link;
    }

    public void setLink(String link){
        this.link = link;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
