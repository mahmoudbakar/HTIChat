package com.undecode.htichat.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LoginResponse{

	@SerializedName("sessionID")
	private String sessionID;

	@SerializedName("user")
	private User user;

	public void setSessionID(String sessionID){
		this.sessionID = sessionID;
	}

	public String getSessionID(){
		return sessionID;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}
}