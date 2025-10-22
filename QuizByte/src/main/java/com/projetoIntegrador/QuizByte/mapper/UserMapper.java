package com.projetoIntegrador.QuizByte.mapper;

import com.projetoIntegrador.QuizByte.DTO.UserDTO;
import com.projetoIntegrador.QuizByte.model.User;

public class UserMapper {
    
     public static void toDTO(UserDTO dto, User entity){
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setPasswordHash(entity.getPasswordHash());
    }

    public static void toEntity(User entity, UserDTO dto){
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPasswordHash(dto.getPasswordHash());
    }
}
