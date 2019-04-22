package com.undecode.htichat.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class UsersResponse{

	@SerializedName("Array")
	private List<LoginResponse> array;

	public void setArray(List<LoginResponse> array){
		this.array = array;
	}

	public List<LoginResponse> getArray(){
		return array;
	}
}