package com.redapp.tasker.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.redapp.tasker.dao.AppRoleRepository;
import com.redapp.tasker.entities.AppUser;
import com.redapp.tasker.entities.PasswordRequest;
import com.redapp.tasker.entities.Stage;
import com.redapp.tasker.entities.StageRequest;
import com.redapp.tasker.entities.Task;
import com.redapp.tasker.entities.TaskRequest;
import com.redapp.tasker.entities.UserRequest;
import com.redapp.tasker.services.PhotosService;
import com.redapp.tasker.services.StagesService;
import com.redapp.tasker.services.TasksServices;
import com.redapp.tasker.services.UsersService;

@RestController
public class MainController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private TasksServices tasksService;
	@Autowired
	private StagesService stagesService;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private PhotosService photosService;
	
//////////////////////public functions //////////////
	@PostMapping("/subscribe")
	public AppUser addUser(
	@RequestBody UserRequest userRequest)
	{
		return this.usersService.addUser(userRequest);
	}
	
	@GetMapping("public/task")
	public Task getTask(
			@RequestParam String taskId) {
		return this.tasksService.getTask(taskId);
	}

	@GetMapping("public/stages")
	public List<Stage> getStages(
			@RequestParam String taskId) {
		return this.stagesService.getStages(taskId);
	}
	
//////////////////////Admin only functions //////////////

@PostMapping("admin/addManager")
public AppUser addManager(
@RequestBody UserRequest userRequest)
{
return this.usersService.addManager(userRequest);
}
@GetMapping("admin/usersList")
public List<AppUser> usersList(
@RequestParam String keyword,
@RequestParam String state)
{
return this.usersService.getUsers(keyword, state);
}

@GetMapping("admin/managersList")
public List<AppUser> managersList(
)
{
return this.usersService.getManagers();
}

@DeleteMapping("admin/deleteManager")
public void deleteManager(
@RequestParam String username
)
{
this.usersService.deleteManager(username);;
}

@DeleteMapping("admin/deleteUser")
public void deleteUser(
@RequestParam String username
)
{
this.usersService.deleteUser(username);;
}

//////// users functions /////
@PutMapping("user/editUser")
public AppUser editUser(
@RequestParam String username,
@RequestBody UserRequest userRequest)
{
return this.usersService.editUser(username,userRequest);
}


@GetMapping("user/getUser")
public AppUser getUser(
@RequestParam String username)
{
return this.usersService.getUser(username);
}

@PutMapping("user/editPassword")
public void editPassword (
@RequestParam String username,
@RequestBody PasswordRequest passwordRequest)
{
 this.usersService.editPassword(username,passwordRequest);
}

@PutMapping("/resetPassword")
public void resetPassword (
@RequestParam String username,
@RequestBody PasswordRequest passwordRequest)
{
 this.usersService.resetPassword(username,passwordRequest.getPassword());
}

@GetMapping("user/tasks")
public List<Task> getTasks(
		@RequestParam String username,
		@RequestParam List status,
		@RequestParam String keyword) {

	return this.tasksService.getTasks(username,status,keyword);
}

@PostMapping("user/addTask")
public void editTask(
		@RequestBody TaskRequest taskRequest) {
	this.tasksService.addTask( taskRequest);
}

@PutMapping("user/editTask")
public void editTask(
		@RequestParam String id,
		@RequestBody TaskRequest taskRequest) {
	this.tasksService.editTask(id, taskRequest);
}

@DeleteMapping("user/deleteTask")
public void deleteTask(
		@RequestParam String id){
	this.tasksService.deleteTask(id);
}



@PutMapping("user/editStage")
public void editStage(
		@RequestParam Long id,
		@RequestBody StageRequest stageRequest) {
	this.stagesService.editStage(id, stageRequest);
}

@PostMapping("user/addStage")
public void addStage(
		@RequestBody StageRequest stageRequest) {
	this.stagesService.addStage(stageRequest);
}

@DeleteMapping("user/deleteStage")
public void deleteStage(
		@RequestParam Long id){
	this.stagesService.deleteStage(id);
}

@DeleteMapping("user/deletePhoto")
public void deletePhoto(
		@RequestParam Long id){
	this.photosService.deletePhoto(id);
}







}
