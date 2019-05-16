package com.undecode.htichat.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class SendMessageRequest{

	@SerializedName("room_id")
	private int roomId;

	@SerializedName("file")
	private String file;

	@SerializedName("message")
	private String message;

	@SerializedName("type")
	private String type;

	public void setRoomId(int roomId){
		this.roomId = roomId;
	}

	public int getRoomId(){
		return roomId;
	}

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"SendMessageRequest{" + 
			"room_id = '" + roomId + '\'' + 
			",file = '" + file + '\'' + 
			",message = '" + message + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}