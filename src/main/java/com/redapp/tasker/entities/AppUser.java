package com.redapp.tasker.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
@Id
private String username;
@NonNull
private String password;
@NonNull
private String name;
private String phone;
private String mail;
private String adress;
@NonNull
private Date subsDate;
@NonNull
private String state;
@OneToMany(mappedBy="worker")
@JsonIgnore
private List<Task> tasks;
@JsonIgnore
@ManyToOne
private AppRole appRole;
public AppUser(String username, @NonNull String password, @NonNull String name, String phone, String mail,
		String adress) {
	super();
	this.username = username;
	this.password = password;
	this.name = name;
	this.phone = phone;
	this.mail = mail;
	this.adress = adress;
	this.subsDate=new Date();
	this.state="waiting";
	this.tasks=new ArrayList<Task>();
}


}
