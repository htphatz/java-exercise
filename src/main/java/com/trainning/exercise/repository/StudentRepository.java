package com.trainning.exercise.repository;

import com.trainning.exercise.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsByEmail(String email);
    Optional<Student> findByEmail(String email);
}
