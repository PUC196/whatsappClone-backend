package com.app.response;

public class AuthResponse {
	
	private String jwt;
	private boolean isAuth;
	
	public AuthResponse(String jwt, boolean isAuth) {
		super();
		this.jwt = jwt;
		this.isAuth = isAuth;
	}
	
	
	 public String getJwt() {
	        return jwt;
	    }

	    public boolean getIsAuth() {
	        return isAuth;
	    }

	    // Setters
	    public void setJwt(String jwt) {
	        this.jwt = jwt;
	    }

	    public void setIsAuth(boolean isAuth) {
	        this.isAuth = isAuth;
	    }
}
