package com.app.request;

public class SingleChatReques {
	
	

	private Integer userId;
	
	public SingleChatReques() {
		// TODO Auto-generated constructor stub
	}

	public SingleChatReques(Integer userId) {
		super();
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "SingleChatReques [userId=" + userId + "]";
	}
	
	
	
	
}
