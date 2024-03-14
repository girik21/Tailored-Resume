package com.resume_tailor.backend.service.Skill;

import com.resume_tailor.backend.model.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getSkills();

    Skill getSkillById(String skillId);

    Skill createSkill(String userId, Skill skill);

    Skill updateSkill(String skillId, Skill skill);

    void deleteSkill(String skillId);
}
