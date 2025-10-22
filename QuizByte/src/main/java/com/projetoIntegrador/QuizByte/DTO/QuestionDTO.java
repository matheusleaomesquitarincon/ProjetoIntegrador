package com.projetoIntegrador.QuizByte.DTO;

import com.projetoIntegrador.QuizByte.model.Question;

import lombok.Data;

@Data
public class QuestionDTO {
    
    private long id;
    private int questionNumber;
    private String statement;
    private String answer;
    private Question.Difficulty difficulty ;
    private String tips;
}
