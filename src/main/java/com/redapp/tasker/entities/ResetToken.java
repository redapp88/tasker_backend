package com.redapp.tasker.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	@ManyToOne
	private AppUser user;
	private String password;
	private Date tokenDate;

	public ResetToken(@NonNull AppUser user,@NonNull String password) {
		this.user = user;
		this.password=password;
		this.tokenDate=new Date();
	}

}
