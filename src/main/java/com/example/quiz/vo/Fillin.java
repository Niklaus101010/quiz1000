package com.example.quiz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fillin {

	// question_Id ==> qId
	private int qId;

	private String question;

	private String options;

	private String answer;

	private String type;

	private boolean necessary;

	public Fillin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fillin(int qId, String question, String options, String answer, String type, boolean necessary) {
		super();
		this.qId = qId;
		this.question = question;
		this.options = options;
		this.answer = answer;
		this.type = type;
		this.necessary = necessary;
	}

	public int getqId() {
		return qId;
	}

	public String getType() {
		return type;
	}

	public String getAnswer() {
		return answer;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public String getOptions() {
		return options;
	}
	
	public String getQuestion() {
		return question;
	}
	
	
}
