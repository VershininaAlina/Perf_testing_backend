package ru.alina.hr_backend_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alina.hr_backend_server.entity.profile.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
