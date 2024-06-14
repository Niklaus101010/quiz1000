package com.example.quiz.constants;

public enum OptionType {

	SINGLE_CHOICE("單選"),//
	MULTIPLE_CHOICE("多選"),//
	TEXT("文字");
	private String type;

	
	private OptionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
