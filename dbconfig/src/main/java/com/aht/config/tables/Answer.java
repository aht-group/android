package com.aht.config.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by roblick on 23.02.2017.
 */

@DatabaseTable(tableName = "answer")
public class Answer {

	@DatabaseField(generatedId = true) private Long id;

	@DatabaseField private Integer integerAnswer;

	@DatabaseField private String textAnswer;

	@DatabaseField private String remark;

	@DatabaseField private Double doubleAnswer;

	@DatabaseField private Double score;

	@DatabaseField private boolean booleanAnswer;

	@DatabaseField(foreign = true, foreignAutoRefresh = true) private Question question;

	@DatabaseField(foreign = true, foreignAutoRefresh = true) private HouseHolds hh;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	public Integer getIntegerAnswer() {return integerAnswer;}
	public void setIntegerAnswer(Integer integerAnswer) {this.integerAnswer = integerAnswer;}

	public String getTextAnswer() {return textAnswer;}
	public void setTextAnswer(String textAnswer) {this.textAnswer = textAnswer;}

	public String getRemark() {return remark;}
	public void setRemark(String remark) {this.remark = remark;}

	public Double getDoubleAnswer() {return doubleAnswer;}
	public void setDoubleAnswer(Double doubleAnswer) {this.doubleAnswer = doubleAnswer;}

	public Double getScore() {return score;}
	public void setScore(Double score) {this.score = score;}

	public boolean isBooleanAnswer() {return booleanAnswer;}
	public void setBooleanAnswer(boolean booleanAnswer) {this.booleanAnswer = booleanAnswer;}

	public Question getQuestion() {return question;}
	public void setQuestion(Question question) {this.question = question;}

	public HouseHolds getHh() {return hh;}
	public void setHh(HouseHolds hh) {this.hh = hh;}
}
