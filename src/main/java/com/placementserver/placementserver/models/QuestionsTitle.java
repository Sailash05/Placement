package com.placementserver.placementserver.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="questiontitle")
public class QuestionsTitle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="questionid")
	private long questionid;
	@Column(name="name")
	private String name;
	@Column(name="filename")
	private String filename;
	
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public QuestionsTitle() {
		super();
    }
	
	public QuestionsTitle(String name, String filename) {
		super();
		this.name = name;
		this.filename = filename;
	}
	
}
