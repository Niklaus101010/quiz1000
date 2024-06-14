package com.example.quiz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {
	private int id;
	
	private String title;
	
	private String type;

	private String option;

	@JsonProperty("is_necessary")
	private boolean necessary;

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Question(int id, String title, String type, String option, boolean necessary) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
		this.option = option;
		this.necessary = necessary;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	

}
