package com.projetoIntegrador.QuizByte.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class NoteDTO {

    private Long id;
    private double score;
    private Date asessmentDate;
}
