package com.resume_tailor.backend.service.Skill;

import com.resume_tailor.backend.model.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getUserSkills(String userId);

    Skill getUserSkillById(String userSkillId);

    Skill createUserSkill(String userId, Skill skill);

    Skill updateUserSkill(String userId, String skillId, Skill skill);

    void deleteUserSkill(String skillId);
}
