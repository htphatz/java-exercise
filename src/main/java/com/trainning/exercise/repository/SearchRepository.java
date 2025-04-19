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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Page<Student> searchStudentsByEntityManager(int pageNumber, int pageSize, String name, String email, String address) {
        StringBuilder sqlSelect = new StringBuilder("SELECT s FROM Student s");
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(s) FROM Student s");
        StringBuilder sqlPredicate = new StringBuilder();

        Map<String, String> params = new HashMap<>();

        // Check if name exist
        if (StringUtils.hasText(name)) {
            appendCondition(sqlPredicate, "s.name LIKE :name");
            params.put("name", "%" + name + "%");
        }

        // Check if email exist
        if (StringUtils.hasText(email)) {
            appendCondition(sqlPredicate, "s.email LIKE :email");
            params.put("email", "%" + email + "%");
        }

        // Check if address exist
        if (StringUtils.hasText(address)) {
            appendCondition(sqlPredicate, "s.address LIKE :address");
            params.put("address", "%" + address + "%");
        }

        // Add predicate into sql
        sqlSelect.append(sqlPredicate);
        sqlCount.append(sqlPredicate);

        // Create query from sql
        Query querySelect = entityManager.createQuery(sqlSelect.toString());
        Query queryCount = entityManager.createQuery(sqlCount.toString());

        // Set params
        params.forEach((key, value) -> {
            querySelect.setParameter(key, value);
            queryCount.setParameter(key, value);
        });

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

    private void appendCondition(StringBuilder predicate, String condition) {
        if (predicate.isEmpty()) {
            predicate.append(" WHERE ").append(condition);
        } else {
            predicate.append(" AND ").append(condition);
        }
    }
}
