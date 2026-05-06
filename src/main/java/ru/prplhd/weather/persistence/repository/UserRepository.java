package ru.prplhd.weather.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prplhd.weather.persistence.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
