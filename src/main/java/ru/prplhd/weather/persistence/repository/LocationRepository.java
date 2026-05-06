package ru.prplhd.weather.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.prplhd.weather.persistence.entity.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
}
