package com.projetoIntegrador.QuizByte.mapper;

import com.projetoIntegrador.QuizByte.DTO.StudentDTO;
import com.projetoIntegrador.QuizByte.model.Student;

public class StudentMapper{
    
    public static StudentDTO toDTO(Student entity) {
        
        StudentDTO dto = new StudentDTO();
        UserMapper.toDTO(dto, entity);
        dto.setEnrollNumber(entity.getEnrollNumber());

        return dto;
    }

    public static Student toEntity(StudentDTO dto) {
        
        if (dto == null) return null;

        Student entity = new Student();
        UserMapper.toEntity(entity, dto);
        entity.setEnrollNumber(dto.getEnrollNumber());
        return entity;
    }
}
