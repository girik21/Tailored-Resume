package com.resume_tailor.backend.dto;

public class Responsibility {
    private String responsibility;

    // Default constructor (required by Jackson)
    public Responsibility() {}

    // Constructor
    public Responsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    // Getters and setters
    public String getDescription() {
        return responsibility;
    }

    public void setDescription(String responsibility) {
        this.responsibility = responsibility;
    }
}
