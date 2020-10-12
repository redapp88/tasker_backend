package com.redapp.tasker.services;

import java.util.List;

import com.redapp.tasker.entities.Task;
import com.redapp.tasker.entities.TaskRequest;

public interface TasksServices {
public Task getTask(String taskID);
public List<Task> getTasks(String username, List status, String keyword);
public Task addTask(TaskRequest taskRequest);
public Task editTask(String taskId,TaskRequest taskRequest); 
public void deleteTask(String taskId); 
}
