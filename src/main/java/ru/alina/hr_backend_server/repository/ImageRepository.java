package ru.alina.hr_backend_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alina.hr_backend_server.entity.image.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
