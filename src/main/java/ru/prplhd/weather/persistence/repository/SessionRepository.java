package ru.prplhd.weather.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prplhd.weather.persistence.entity.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
}
