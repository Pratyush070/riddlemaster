package com.pratyush.riddlemaster.model;

public class Riddle {
    private String id;
    private String question;
    private String answer;
    private String category;

    public Riddle() {}

    public Riddle(String id, String question, String answer, String category) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.category = category;
    }

    public String getId(){ return id; }
    public String getQuestion(){ return question; }
    public String getAnswer(){ return answer; }
    public String getCategory(){ return category; }
}
