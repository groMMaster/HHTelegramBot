package ru.relax.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.relax.entity.ViewedVacancy;

public interface PastVacancyDAO extends JpaRepository<ViewedVacancy, Long> {
}
