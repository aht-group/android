package com.aht.config.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * Created by krueger on 25.01.2017.
 */

@DatabaseTable(tableName = "question")
public class Question {

    @DatabaseField(generatedId = true)
    private Long id;

	/** systemId represents the ID given from main system*/
    @DatabaseField private Long systemId;

	@DatabaseField private String code;

    @DatabaseField private String question;

    @DatabaseField private Date dateCreated;

    @DatabaseField private Date dueDate;

	@DatabaseField private Integer position;

	@DatabaseField private String remark;

	@DatabaseField private Double minScore;

	@DatabaseField private Double maxScore;

	@DatabaseField private boolean calcScore;

	@DatabaseField private boolean showInteger;

	@DatabaseField private boolean showText;

	@DatabaseField private boolean showRemark;

	@DatabaseField private boolean showDouble;

	@DatabaseField private boolean showScore;

	@DatabaseField private boolean showBoolean;

	@DatabaseField private boolean showSelectOne;

	@DatabaseField private boolean showSelectMulti;

	@DatabaseField private boolean visible;

    //Many_to_One
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Section section;

	@ForeignCollectionField
	private Collection<Answer> answers;

    public Section getSurvey() {return section;    }
    public void setSurvey(Section survey) {this.section = survey;    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getQuestion() {return question;}
    public void setQuestion(String question) {this.question = question;}

    public Collection<Answer> getAnswers() {
		if(answers == null) return new ArrayList<>();
		return answers;
	}
    public void setAnswer(Collection<Answer> answers) {this.answers = answers;}

    public Date getDateCreated() {return dateCreated;}
    public void setDateCreated(Date dateCreated) {this.dateCreated = dateCreated;}

    public Date getDueDate() {return dueDate;}
    public void setDueDate(Date dueDate) {this.dueDate = dueDate;}


    public Question(String question, Collection<Answer> answers, Date dateCreated, Date dueDate) {
        this.question = question;
        this.answers = answers;
        this.dateCreated = dateCreated;
        this.dueDate = dueDate;
    }

    public Question(){}

	public boolean isShowInteger() {return showInteger;}
	public void setShowInteger(boolean showInteger) {this.showInteger = showInteger;}

	public Long getSystemId() {return systemId;}
	public void setSystemId(Long systemId) {this.systemId = systemId;}

	public boolean isShowText() {return showText;}
	public void setShowText(boolean showText) {this.showText = showText;}

	public boolean isShowRemark() {return showRemark;}
	public void setShowRemark(boolean showRemark) {this.showRemark = showRemark;}

	public boolean isShowDouble() {return showDouble;}
	public void setShowDouble(boolean showDouble) {this.showDouble = showDouble;}

	public boolean isShowScore() {return showScore;}
	public void setShowScore(boolean showScore) {this.showScore = showScore;}

	public boolean isShowBoolean() {return showBoolean;}
	public void setShowBoolean(boolean showBoolean) {this.showBoolean = showBoolean;}

	public boolean isShowSelectOne() {return showSelectOne;}
	public void setShowSelectOne(boolean showSelectOne) {this.showSelectOne = showSelectOne;}

	public boolean isShowSelectMulti() {return showSelectMulti;}
	public void setShowSelectMulti(boolean showSelectMulti) {this.showSelectMulti = showSelectMulti;}

	public Integer getPosition() {return position;}
	public void setPosition(Integer position) {this.position = position;}

	public String getRemark() {return remark;}
	public void setRemark(String remark) {this.remark = remark;}

	public Double getMinScore() {return minScore;}
	public void setMinScore(Double minScore) {this.minScore = minScore;}

	public Double getMaxScore() {return maxScore;}
	public void setMaxScore(Double maxScore) {this.maxScore = maxScore;}

	public boolean getCalcScore() {return calcScore;}
	public void setCalcScore(boolean calcScore) {this.calcScore = calcScore;}

	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}

	public boolean isVisible() {return visible;}
	public void setVisible(boolean visible) {this.visible = visible;}
}
