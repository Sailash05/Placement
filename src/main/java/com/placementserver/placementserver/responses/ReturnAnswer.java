package com.placementserver.placementserver.responses;

public class ReturnAnswer {
	
	private long questionid;
	private String questionName;
	private float markpercentage;
	private int correct;
	private int wrong;

	public long getQuestionid() {
		return questionid;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
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

	public ReturnAnswer() {
		super();
	}

	public ReturnAnswer(long questionid, float markpercentage, int correct, int wrong, String questionName) {
		super();
		this.questionid = questionid;
		this.markpercentage = markpercentage;
		this.correct = correct;
		this.wrong = wrong;
		this.questionName = questionName;
	}

//	public ReturnAnswer(long questionid, float markpercentage, int correct, int wrong) {
//		super();
//		this.questionid = questionid;
//		this.markpercentage = markpercentage;
//		this.correct = correct;
//		this.wrong = wrong;
//	}
	
	
	
}
