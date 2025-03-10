package com.placementserver.placementserver.models.questionrequest;

import com.placementserver.placementserver.models.Questions;

import java.util.List;

public class QuestionRequestDTO {
    public String name;
    public String dateTime;
    public List<Questions> questions;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public List<Questions> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public QuestionRequestDTO(String name, String dateTime, List<Questions> questions) {
        super();
        this.name = name;
        this.dateTime = dateTime;
        this.questions = questions;
    }
}
