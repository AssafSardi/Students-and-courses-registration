package com.checkpoint.StudentsCourses.repository;

import com.checkpoint.StudentsCourses.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findBySessionKey(String sessionKey);
    void deleteBySessionKey(String sessionKey);
}
