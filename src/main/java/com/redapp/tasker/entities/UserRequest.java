package com.redapp.tasker.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	
	private String username;
	private String password;
	private String name;
	private String phone;
	private String mail;
	private String adress;
	private String state;
}