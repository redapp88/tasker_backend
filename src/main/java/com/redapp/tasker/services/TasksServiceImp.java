package com.redapp.tasker.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redapp.tasker.dao.TaskRepository;
import com.redapp.tasker.entities.AppUser;
import com.redapp.tasker.entities.Task;
import com.redapp.tasker.entities.TaskRequest;
@Service
@Transactional
public class TasksServiceImp implements TasksServices {
@Autowired
private TaskRepository taskRepository;
@Autowired
private UsersService usersService;
	@Override
	public List<Task> getTasks(String username, List status, String keyword) {
		System.out.println(username);
		System.out.println(status);
if(keyword.equals("")){
	keyword="%%";
}
else {
	keyword="%"+keyword+"%";
}
		return this.taskRepository.findByDetails(username,status,keyword);
	}

	@Override
	public Task addTask(TaskRequest taskRequest) {
	AppUser user=this.usersService.findByUsername(taskRequest.getWorkerUsername());
	String key=this.generateUniqueKey();
	while(this.taskRepository.findById(key).isPresent()) {
	key=this.generateUniqueKey();
	System.out.println(key);
	}
	
	Task task=new Task(key,taskRequest.getTitle(),taskRequest.getDescription(),taskRequest.getClient(),user);
	return this.taskRepository.save(task);
	
	}

	@Override
	public Task editTask(String taskId,TaskRequest taskRequest) {
		Optional<Task> taskOpt=this.taskRepository.findById(taskId);
		if(!taskOpt.isPresent())
			throw new RuntimeException("Travail Introuvable");
		Task task = taskOpt.get();
		task.setTitle(taskRequest.getTitle());
		task.setDescription(taskRequest.getDescription());
		task.setClient(taskRequest.getClient());
		task.setState(taskRequest.getState());
		return task;
		
		
	}

	@Override
	public void deleteTask(String taskId) {
		this.taskRepository.delete(this.getTask(taskId));

	}

	@Override
	public Task getTask(String taskId) {
		Optional<Task> taskOpt=this.taskRepository.findById(taskId);
		if(!taskOpt.isPresent())
			throw new RuntimeException("Travail introuvable");
		else return taskOpt.get();
	
	}

	private String generateUniqueKey() {
		return RandomStringUtils.randomAlphanumeric(8);

	}
}
