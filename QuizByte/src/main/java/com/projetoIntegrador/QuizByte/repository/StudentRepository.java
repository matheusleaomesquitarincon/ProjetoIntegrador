package com.projetoIntegrador.QuizByte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegrador.QuizByte.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
}
