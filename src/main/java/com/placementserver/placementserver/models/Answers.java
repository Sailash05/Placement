package com.placementserver.placementserver.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="answer")
public class Answers {
	
	@Id
	@Column(name="ansid")	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long uniqueid;
	@ManyToOne(fetch = FetchType.LAZY) // Establishing the relationship
    @JoinColumn(name="rollno", referencedColumnName = "rollno") // `rollno` in `Answers` refers to `rollno` in `Student`
    private Student student; // Assuming `Student` is the entity representing the student table
	@Column(name="questionid")
	private long questionid;
	@Column(name="markpercentage")
	private float markpercentage;
	@Column(name="correct")
	private int correct;
	@Column(name="wrong")
	private int wrong;
	
	public long getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(long uniqueid) {
		this.uniqueid = uniqueid;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public long getQuestionid() {
		return questionid;
	}
	public void setQuestionid(long questionid) {
		this.questionid = questionid;
	}
	public float getMarkpercentage() {
		return markpercentage;
	}
	public void setMarkpercentage(float markpercentage) {
		this.markpercentage = markpercentage;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getWrong() {
		return wrong;
	}
	public void setWrong(int wrong) {
		this.wrong = wrong;
	}
	
	public Answers() {
		super();
	}
	
	public Answers(long uniqueid, Student student, long questionid, float markpercentage, int correct, int wrong) {
		super();
		this.uniqueid = uniqueid;
		this.student = student;
		this.questionid = questionid;
		this.markpercentage = markpercentage;
		this.correct = correct;
		this.wrong = wrong;
	}
}
