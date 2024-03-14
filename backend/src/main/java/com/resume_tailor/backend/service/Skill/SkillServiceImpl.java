package com.resume_tailor.backend.service.Skill;

import com.resume_tailor.backend.model.Skill;
import com.resume_tailor.backend.model.User;
import com.resume_tailor.backend.repository.SkillRepository;
import com.resume_tailor.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Skill getSkillById(String skillId) {
        Optional<Skill> userSkill = skillRepository.findById(skillId);
        return userSkill.orElse(null);
    }

    @Override
    public Skill createSkill(String userId, Skill skill) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Skill savedSkill = skillRepository.save(skill);

            // Associate with user
            user.addSkill(savedSkill);
            userRepository.save(user);

            return savedSkill;
        }
        return null;
    }

    @Override
    public Skill updateSkill(String skillId, Skill updatedSkill) {
        updatedSkill.setId(skillId);
        return skillRepository.save(updatedSkill);
    }

    @Override
    public void deleteSkill(String skillId) {
        skillRepository.deleteById(skillId);
    }
}

