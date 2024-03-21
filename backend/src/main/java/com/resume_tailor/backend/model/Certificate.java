package com.resume_tailor.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "certifications")
public class Certificate {
    @Id
    private String id;

    @NotBlank(message = "Certification name can not be null!")
    private String name;

    @NotNull(message = "Start date cannot be null.")
    private Date startDate;

    private Date endDate;

    @NotBlank(message = "Issuer cannot be null or empty.")
    private String issuer;

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

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
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

    public void setDescription(String description) {
        this.description = description;
    }
}
