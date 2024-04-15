package ru.alina.hr_backend_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alina.hr_backend_server.entity.profile.Experience;
import ru.alina.hr_backend_server.entity.user.User;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Experience getExperienceByIdAndUser(Long id, User user);
}
