package com.resume_tailor.backend.controller;

import com.resume_tailor.backend.model.Skill;
import com.resume_tailor.backend.service.Skill.SkillService;
import com.resume_tailor.backend.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Skill>>> getSkills() {
        try {
            List<Skill> skills = skillService.getSkills();
            String successMessage = "Successfully retrieved user's skills.";
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, successMessage, skills));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<?> getSkillById(@PathVariable String skillId) {
        try {
            Skill skill = skillService.getSkillById(skillId);
            if (skill != null) {
                return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User Skill retrieved successfully.", skill));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Skill>> createSKill(@RequestParam("userId") String userId, @Valid @RequestBody Skill skill) {
        try {
            Skill createdSkill = skillService.createSkill(userId, skill);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(true, "User skill created successfully.", createdSkill));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<ResponseWrapper<Skill>> updateSkill(@PathVariable String skillId, @Valid @RequestBody Skill updatedSkill) {
        try {
            Skill skill = skillService.updateSkill(skillId, updatedSkill);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User skill updated successfully.", updatedSkill));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteSkill(@PathVariable String skillId) {
        try {
            skillService.deleteSkill(skillId);
            return ResponseEntity.ok().body(new ResponseWrapper<>(true, "User skill deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(false, e.getMessage(), null));
        }
    }
}
