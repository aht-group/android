package com.aht.config.tables;

import com.aht.config.tables.Question;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by roblick on 23.02.2017.
 */
@DatabaseTable(tableName = "section")
public class Section {

		@DatabaseField(generatedId = true)
		private Long id;

		/** systemId represents the ID given from main system*/
		@DatabaseField
		private Long systemId;

		@DatabaseField
		private String name;

		//Many_to_One
		@ForeignCollectionField
		private Collection<Question> questions;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	public Long getSystemId() {return systemId;}
	public void setSystemId(Long systemId) {this.systemId = systemId;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public Collection<Question> getQuestions() {return questions;}
	public void setQuestions(Collection<Question> questions) {this.questions = questions;}
}

