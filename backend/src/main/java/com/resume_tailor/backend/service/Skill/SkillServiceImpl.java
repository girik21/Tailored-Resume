package com.resume_tailor.backend.service.Skill;

import com.resume_tailor.backend.model.Skill;
import com.resume_tailor.backend.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public List<Skill> getUserSkills(String userId) {
        return skillRepository.findByUserId(userId);
    }

    @Override
    public Skill getUserSkillById(String userSkillId) {
        Optional<Skill> userSkill = skillRepository.findById(userSkillId);
        return userSkill.orElse(null);
    }

    @Override
    public Skill createUserSkill(String userId, Skill skill) {
        skill.setUserId(userId);
        return skillRepository.save(skill);
    }

    @Override
    public Skill updateUserSkill(String userId, String skillId, Skill updatedSkill) {
        updatedSkill.setId(skillId);
        updatedSkill.setUserId(userId);
        return skillRepository.save(updatedSkill);
    }

    @Override
    public void deleteUserSkill(String skillId) {
        skillRepository.deleteById(skillId);
    }
}

