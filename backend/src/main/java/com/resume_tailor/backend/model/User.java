package com.resume_tailor.backend.model;

import com.resume_tailor.backend.security.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    @NotBlank(message = "Username cannot be null!")
    private String username;
    @Indexed(unique = true)
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
    @CreatedDate
    private Date createdDate;
    @DBRef
    private List<Experience> experiences = new ArrayList<>();
    @DBRef
    private List<Education> education = new ArrayList<>();
    @DBRef
    private List<Project> projects = new ArrayList<>();
    @DBRef
    private List<Skill> skills = new ArrayList<>();
    @DBRef
    private List<Certificate> certificates = new ArrayList<>();
    @DBRef
    private List<Resume> resumes = new ArrayList<>();
    @DBRef
    private List<CoverLetter> coverLetters = new ArrayList<>();

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public void addExperience(Experience experience) {
        this.experiences.add(experience);
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public void addEducation(Education education) {
        this.education.add(education);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }


    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public void addCertificate(Certificate certificate) {
        this.certificates.add(certificate);
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }

    public void addResume(Resume resume) {
        this.resumes.add(resume);
    }

    public List<CoverLetter> getCoverLetters() {
        return coverLetters;
    }

    public void setCoverLetters(List<CoverLetter> coverLetters) {
        this.coverLetters = coverLetters;
    }

    public void addCoverLetter(CoverLetter coverLetter) {
        this.coverLetters.add(coverLetter);
    }
}