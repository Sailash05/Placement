package com.placementserver.placementserver.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="question")
public class Questions {
	
	@Id
	@Column(name="qid")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long uniqueid;
	@Column(name="questionid")
	private long questionid;
	@Column(name="question")
	private String question;
	@Column(name="option_a")
	private String a;
	@Column(name="option_b")
	private String b;
	@Column(name="option_c")
	private String c;
	@Column(name="option_d")
	private String d;
	@Column(name="answer")
	private String answer;
	@Column(name="type")
	private String type;
	
	public long getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(long uniqueid) {
		this.uniqueid = uniqueid;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Questions() {
		super();
	}
	
	public Questions(long questionid, String question, String answer, String type) {
		super();
		this.questionid = questionid;
		this.question = question;
		this.answer = answer;
		this.type = type;
	}
	public Questions(long questionid, String question, String a, String b, String c, String d, String answer,
			String type) {
		super();
		this.questionid = questionid;
		this.question = question;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.answer = answer;
		this.type = type;
	}
}