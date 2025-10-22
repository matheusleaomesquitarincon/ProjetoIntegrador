package com.projetoIntegrador.QuizByte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegrador.QuizByte.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
    
}
