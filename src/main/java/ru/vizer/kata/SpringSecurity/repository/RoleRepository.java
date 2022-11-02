package ru.vizer.kata.SpringSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vizer.kata.SpringSecurity.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}