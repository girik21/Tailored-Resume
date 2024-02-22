package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;


}
