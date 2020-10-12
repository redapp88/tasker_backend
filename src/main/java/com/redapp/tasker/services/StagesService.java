package com.redapp.tasker.services;

import java.util.List;

import com.redapp.tasker.entities.Photo;
import com.redapp.tasker.entities.Stage;
import com.redapp.tasker.entities.StageRequest;


public interface StagesService {
	public Stage getStage(Long stageId);
	public List<Stage> getStages(String taskId);
	public Stage addStage(StageRequest stageRequest);
	public Stage editStage(Long stageId,StageRequest stageRequest); 
	public void deleteStage(Long stageId);
	public void addPhoto(Long stage_id,Long id);
	public void deletePhoto(Long stage_id,Long id);	
	
}
