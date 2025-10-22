package com.projetoIntegrador.QuizByte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegrador.QuizByte.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
