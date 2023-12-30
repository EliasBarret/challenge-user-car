package com.challangepitang.systemcar.repository;

import com.challangepitang.systemcar.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    User findByEmail(String username);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
}