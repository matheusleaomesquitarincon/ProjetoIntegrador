package com.projetoIntegrador.QuizByte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegrador.QuizByte.model.Matter;

public interface MatterRepository extends JpaRepository<Matter, Long>{
    
}
