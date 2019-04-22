package com.undecode.htichat.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LoginResponse{

	@SerializedName("date_birth")
	private String dateBirth;

	@SerializedName("image")
	private String image;

	@SerializedName("password")
	private String password;

	@SerializedName("gender")
	private boolean gender;

	@SerializedName("phone")
	private String phone;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("date_seen")
	private String dateSeen;

	@SerializedName("name")
	private String name;

	@SerializedName("active")
	private boolean active;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public void setDateBirth(String dateBirth){
		this.dateBirth = dateBirth;
	}

	public String getDateBirth(){
		return dateBirth;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setGender(boolean gender){
		this.gender = gender;
	}

	public boolean isGender(){
		return gender;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setDateCreated(String dateCreated){
		this.dateCreated = dateCreated;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public void setDateSeen(String dateSeen){
		this.dateSeen = dateSeen;
	}

	public String getDateSeen(){
		return dateSeen;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setActive(boolean active){
		this.active = active;
	}

	public boolean isActive(){
		return active;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}