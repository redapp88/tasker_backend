package com.redapp.tasker.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ExtendedUser extends User {
	private String name;
	private String state;
public ExtendedUser(String username, String password,String name,String state, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.name=name;

	}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}



}
