package com.example.quiz.vo;

import java.time.LocalDateTime;

public class Feedback {

	private String userName;

	private LocalDateTime fillinDateTime;

	private FeedbackDetail feedBackDetail;
	
	public Feedback() {
		super();
	}
	
	public Feedback(String userName, LocalDateTime fillinDateTime, FeedbackDetail feedBackDetail) {
		super();
		this.userName = userName;
		this.fillinDateTime = fillinDateTime;
		this.feedBackDetail = feedBackDetail;
	}

	public String getUserName() {
		return userName;
	}

	public LocalDateTime getFillinDateTime() {
		return fillinDateTime;
	}
	
	public FeedbackDetail getFeedBackDetail() {
		return feedBackDetail;
	}
}
