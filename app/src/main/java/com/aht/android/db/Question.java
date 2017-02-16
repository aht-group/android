package com.aht.android.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


/**
 * Created by krueger on 25.01.2017.
 */

@DatabaseTable(tableName = "question")
public class Question {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String question;

    @DatabaseField
    private String answer;

    @DatabaseField
    private Date dateCreated;

    @DatabaseField
    private Date dueDate;

    //Many_to_One
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "Question")
    private Survey survey;

    public Survey getSurvey() {return survey;    }
    public void setSurvey(Survey survey) {this.survey = survey;    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getQuestion() {return question;}
    public void setQuestion(String question) {this.question = question;}

    public String getAnswer() {return answer;}
    public void setAnswer(String answer) {this.answer = answer;}

    public Date getDateCreated() {return dateCreated;}
    public void setDateCreated(Date dateCreated) {this.dateCreated = dateCreated;}

    public Date getDueDate() {return dueDate;}
    public void setDueDate(Date dueDate) {this.dueDate = dueDate;}


    public Question(String question, String answer, Date dateCreated, Date dueDate) {
        this.question = question;
        this.answer = answer;
        this.dateCreated = dateCreated;
        this.dueDate = dueDate;
    }

    public Question(){}
}
