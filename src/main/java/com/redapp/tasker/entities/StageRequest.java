package com.redapp.tasker.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageRequest {
	private Long id;
	private String comment;
	private String taskId;
	private List<Photo> photos;
}
