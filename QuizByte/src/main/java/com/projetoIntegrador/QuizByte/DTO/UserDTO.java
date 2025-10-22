package com.projetoIntegrador.QuizByte.DTO;

import lombok.Data;

@Data
public abstract class UserDTO {
    
    private long id;
    private String name;
    private String username;
    private String email;
    private String passwordHash;
}
