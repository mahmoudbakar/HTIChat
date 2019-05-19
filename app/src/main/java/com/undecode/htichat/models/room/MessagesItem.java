package com.undecode.htichat.models.room;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class MessagesItem{

	@SerializedName("room_id")
	private int roomId;

	@SerializedName("file")
	private String file;

	@SerializedName("send_date")
	private String sendDate;

	@SerializedName("id")
	private int id;

	@SerializedName("message")
	private String message;

	@SerializedName("type")
	private String type;

	@SerializedName("sender_id")
	private int senderId;

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

	public void setSendDate(String sendDate){
		this.sendDate = sendDate;
	}

	public String getSendDate(){
		return sendDate;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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

	public void setSenderId(int senderId){
		this.senderId = senderId;
	}

	public int getSenderId(){
		return senderId;
	}
}