package com.soro.esop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
