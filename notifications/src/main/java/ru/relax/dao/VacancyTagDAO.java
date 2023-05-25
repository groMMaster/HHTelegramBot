package ru.relax.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relax.entity.VacancyTag;

@Repository
public interface VacancyTagDAO extends JpaRepository<VacancyTag, Long> {
}
