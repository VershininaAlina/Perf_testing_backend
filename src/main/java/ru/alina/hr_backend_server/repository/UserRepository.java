package ru.alina.hr_backend_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alina.hr_backend_server.entity.user.Role;
import ru.alina.hr_backend_server.entity.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    User getUserByEmail(String email);

    User getUserByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    User getUserById(Long id);

    List<User> getUserByRole(Role role);
}
