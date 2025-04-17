package com.trainning.exercise.repository;

import com.trainning.exercise.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Page<Student> searchStudentsByEntityManager(int pageNumber, int pageSize, String name, String email, String address) {
        StringBuilder sqlSelect = new StringBuilder("SELECT s FROM Student s WHERE 1=1");
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(*) FROM Student s WHERE 1=1");

        // Check if name exist
        if (StringUtils.hasLength(name)) {
            sqlSelect.append(" AND s.name LIKE (?1)");
            sqlCount.append(" AND s.name LIKE (?1)");
        }

        // Check if email exist
        if (StringUtils.hasLength(email)) {
            sqlSelect.append(" AND s.email LIKE (?2)");
            sqlCount.append(" AND s.email LIKE (?2)");
        }

        // Check if address exist
        if (StringUtils.hasLength(address)) {
            sqlSelect.append(" AND s.address LIKE (?3)");
            sqlCount.append(" AND s.address LIKE (?3)");
        }

        // Select students and count students
        Query querySelect = entityManager.createQuery(sqlSelect.toString());
        Query queryCount= entityManager.createQuery(sqlCount.toString());
        if (StringUtils.hasLength(name)) {
            querySelect.setParameter(1, "%" + name +"%");
            queryCount.setParameter(1, "%" + name +"%");
        }

        if (StringUtils.hasLength(email)) {
            querySelect.setParameter(2, "%" + email +"%");
            queryCount.setParameter(2, "%" + email +"%");
        }

        if (StringUtils.hasLength(address)) {
            querySelect.setParameter(3, "%" + address +"%");
            queryCount.setParameter(3, "%" + address +"%");
        }

        log.info("Select SQL: {}", sqlSelect.toString());
        log.info("Count SQL: {}", sqlCount.toString());

        // Select
        querySelect.setFirstResult(pageNumber);
        querySelect.setMaxResults(pageSize);
        List<Student> students = (List<Student>) querySelect.getResultList();

        // Count
        Long totalStudents = (Long) queryCount.getSingleResult();

        // Create pageable
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return new PageImpl<>(students, pageable, totalStudents);
    }

    public Page<Student> searchStudentsByJdbcTemplate(int pageNumber, int pageSize, String name, String email, String address) {
        StringBuilder sqlSelect = new StringBuilder("SELECT * FROM students WHERE 1=1");
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(*) FROM students WHERE 1=1");

        // Params for select and count SQL
        List<Object> paramsSelect = new ArrayList<>();
        List<Object> paramsCount = new ArrayList<>();

        // Check if name exists
        if (StringUtils.hasLength(name)) {
            sqlSelect.append(" AND name LIKE ?");
            sqlCount.append(" AND name LIKE ?");
            paramsSelect.add("%" + name + "%");
            paramsCount.add("%" + name + "%");
        }

        // Check if email exists
        if (StringUtils.hasLength(email)) {
            sqlSelect.append(" AND email LIKE ?");
            sqlCount.append(" AND email LIKE ?");
            paramsSelect.add("%" + email + "%");
            paramsCount.add("%" + email + "%");
        }

        // Check if address exists
        if (StringUtils.hasLength(address)) {
            sqlSelect.append(" AND address LIKE ?");
            sqlCount.append(" AND address LIKE ?");
            paramsSelect.add("%" + address + "%");
            paramsCount.add("%" + address + "%");
        }

        // Select students
        sqlSelect.append(" LIMIT ? OFFSET ?");
        paramsSelect.add(pageSize);
        paramsSelect.add(pageNumber * pageSize);
        List<Student> students = jdbcTemplate.query(sqlSelect.toString(), paramsSelect.toArray(), new BeanPropertyRowMapper<>(Student.class));

        // Count students
        Long totalStudents = jdbcTemplate.queryForObject(sqlCount.toString(), paramsCount.toArray(), Long.class);

        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new PageImpl<>(students, pageable, totalStudents);
    }
}
