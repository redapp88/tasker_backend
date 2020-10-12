package com.redapp.tasker.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
	@Id
private String id;
@NonNull
private String title;
@NonNull
private String description;
private String client;
@NonNull
private Date creatDate;
@NonNull
private String state;
@ManyToOne

private AppUser worker;
@OneToMany(mappedBy="task")
@JsonIgnore
private List<Stage> stages=new ArrayList<Stage>();
public Task(@NonNull String id,@NonNull String title, @NonNull String description, String client, AppUser worker) {
	super();
	this.id=id;
	this.title = title;
	this.description = description;
	this.client = client;
	this.worker = worker;
	this.state="current";
	this.creatDate=new Date();
	this.stages=new ArrayList<Stage>();
}



}
