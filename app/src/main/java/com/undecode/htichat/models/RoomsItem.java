package com.undecode.htichat.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class RoomsItem{

	@SerializedName("room_id")
	private int roomId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("id")
	private int id;

	@SerializedName("users")
	private List<User> users;

	public void setRoomId(int roomId){
		this.roomId = roomId;
	}

	public int getRoomId(){
		return roomId;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUsers(List<User> users){
		this.users = users;
	}

	public List<User> getUsers(){
		return users;
	}
}