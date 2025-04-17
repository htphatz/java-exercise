package com.trainning.exercise.repository;

import com.trainning.exercise.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsByEmail(String email);
    Optional<Student> findByEmail(String email);

    @Query("SELECT s FROM Student s WHERE " +
            "(:name IS NULL OR :name = '' OR s.name LIKE %:name%) AND " +
            "(:email IS NULL OR :email = '' OR s.email LIKE %:email%) AND " +
            "(:address IS NULL OR :address = '' OR s.address LIKE %:address%)")
    Page<Student> searchStudents(@Param("name") String name,
                                 @Param("email") String email,
                                 @Param("address") String address,
                                 Pageable pageable);
}
