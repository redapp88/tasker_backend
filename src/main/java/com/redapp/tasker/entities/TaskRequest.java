package com.redapp.tasker.entities;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
	private String id;
	private String title;
	private String description;
	private String client;
	private String state;
	private String workerUsername;
}
