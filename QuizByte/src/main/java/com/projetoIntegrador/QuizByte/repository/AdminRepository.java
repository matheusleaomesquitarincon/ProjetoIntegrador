package com.projetoIntegrador.QuizByte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegrador.QuizByte.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    
}
