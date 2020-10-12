package com.redapp.tasker.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stage implements Serializable {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
@NonNull
private String comment;
@NonNull
private Date stageDate;
@ManyToOne
private Task task;
@OneToMany
List<Photo> photos;
public Stage(@NonNull String comment, Task task) {
	super();
	this.comment = comment;
	this.stageDate = new Date();
	this.task = task;
	this.photos=new ArrayList<Photo>();
	  
}

public Stage(@NonNull String comment, Task task,List <Photo> photos) {
	super();
	this.comment = comment;
	this.stageDate = new Date();
	this.task = task;
	this.photos=photos;
	  
}

}
