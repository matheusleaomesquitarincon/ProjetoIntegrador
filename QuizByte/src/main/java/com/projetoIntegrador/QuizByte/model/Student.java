package com.projetoIntegrador.QuizByte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends User{
    
    private long enrollNumber;

    public long getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(long enrollNumber) {
        this.enrollNumber = enrollNumber;
    } 
}
