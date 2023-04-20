package ru.relax.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.relax.entity.RawData;

public interface RawDataDAO extends JpaRepository<RawData, Long> {
}
