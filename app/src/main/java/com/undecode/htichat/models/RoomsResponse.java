package com.undecode.htichat.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class RoomsResponse{

	@SerializedName("rooms")
	private List<RoomsItem> rooms;

	public void setRooms(List<RoomsItem> rooms){
		this.rooms = rooms;
	}

	public List<RoomsItem> getRooms(){
		return rooms;
	}
}