package ru.relax.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.relax.entity.User;
import ru.relax.entity.VacancyTag;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
}
