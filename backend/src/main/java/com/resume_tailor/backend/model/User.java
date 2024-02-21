package com.resume_tailor.backend.model;

import com.resume_tailor.backend.security.Role;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    @NotBlank(message = "Username cannot be null!")
    private String username;
    @Email(message = "Enter valid email!")
    private String email;
    @NotBlank(message = "Phone number cannot be null!")
    private String phone;
    @NotBlank(message = "Password cannot be null!")
    private String password;
    @NotNull(message = "Address 1 cannot be null!")
    private String address1;
    private String address2;
    @NotNull(message = "City cannot be null!")
    private String city;
    @NotNull(message = "State cannot be null!")
    private String state;
    @NotNull(message = "Zip cannot be null!")
    private String zip;
    @NotNull(message = "Linked in Link cannot be null!")
    private String linkedinLink;
    @NotNull(message = "Portfolio link cannot be null!")
    private String portfolioLink;
    private String professionalSummary;
    @NotNull(message = "Role cannot be null!")
    private Role role;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }

    public String getPortfolioLink() {
        return portfolioLink;
    }

    public void setPortfolioLink(String portfolioLink) {
        this.portfolioLink = portfolioLink;
    }

    public String getProfessionalSummary() {
        return professionalSummary;
    }

    public void setProfessionalSummary(String professionalSummary) {
        this.professionalSummary = professionalSummary;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}