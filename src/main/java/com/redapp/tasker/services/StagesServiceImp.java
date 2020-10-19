package com.redapp.tasker.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redapp.tasker.dao.StageRepository;
import com.redapp.tasker.entities.Photo;
import com.redapp.tasker.entities.Stage;
import com.redapp.tasker.entities.StageRequest;
import com.redapp.tasker.entities.Task;
@Service
@Transactional
public class StagesServiceImp implements StagesService {
@Autowired
private StageRepository stageRepository;
@Autowired
private TasksServices tasksService;
@Autowired
private PhotosService photosService;
	@Override
	public List<Stage> getStages(String taskId) {
		return this.stageRepository.findByTask(taskId);
	}

	@Override
	public Stage addStage(StageRequest stageRequest) {
	Task task=this.tasksService.getTask(stageRequest.getTaskId());
	Stage stage=new Stage(stageRequest.getComment(), task,stageRequest.getPhotos());
	return this.stageRepository.save(stage);
	
	}

	@Override
	public Stage editStage(Long stageId,StageRequest stageRequest) {
		Stage stage=this.getStage(stageId);
		stage.setComment(stageRequest.getComment());
		stage.getPhotos().clear();
		stage.setPhotos(stageRequest.getPhotos());
		this.stageRepository.save(stage);
		return stage;
		}

	@Override
	public void deleteStage(Long stageId) {
		Stage stage=this.getStage(stageId);
		this.stageRepository.delete(stage);

	}



	@Override
	public Stage getStage(Long stageId) {
		Optional<Stage> stageOpt=this.stageRepository.findById(stageId);
		if(!stageOpt.isPresent())
			throw new RuntimeException("Etape introuvable");
		else return stageOpt.get();
	}

	@Override
	public void addPhoto(Long stageId,Long id) {
		
 this.getStage(stageId).getPhotos().add(this.photosService.getPhoto(id));
	}

	@Override
	public void deletePhoto(Long stageId,Long id) {
		Stage stage=this.getStage(stageId);
		Photo PhotoTemp = new Photo(id,null,null,null);
		 if(stage.getPhotos().contains(PhotoTemp)) {
			stage.getPhotos().remove(PhotoTemp);
			try {
				this.photosService.deletePhoto(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
	}

}
