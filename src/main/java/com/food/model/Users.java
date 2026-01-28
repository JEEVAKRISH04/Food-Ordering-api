package com.food.model;

import com.tap.enums.UserRole;

public class Users {
	private int userId;
	private String name;
	private String email;
	private String phoneNo;
	private String userName;
	private UserRole role;
	private String password;
	private String address;

	public Users() {
	}

	public Users(int userId, String name, String email, String userName, String password, UserRole role, String phoneNo, String address) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.phoneNo = phoneNo;
		this.password = password;
		this.role = role;
		this.userName = userName;
		this.address = address;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		
		this.phoneNo = phoneNo;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public  UserRole getRole() {
		return role;
	}

	public void setRole( UserRole role) {
		this.role = role;
	}
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", name=" + name + ", email=" + email + ", phoneNo=" + phoneNo
				+ ", userName=" + userName + ", role=" + role + ", password=" + password + ", address=" + address + "]";
	}
	

}
