package com.redapp.tasker;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.redapp.tasker.dao.AppRoleRepository;
import com.redapp.tasker.entities.AppRole;
import com.redapp.tasker.entities.AppUser;
import com.redapp.tasker.entities.Photo;
import com.redapp.tasker.entities.Stage;
import com.redapp.tasker.entities.StageRequest;
import com.redapp.tasker.entities.Task;
import com.redapp.tasker.entities.TaskRequest;
import com.redapp.tasker.entities.UserRequest;
import com.redapp.tasker.services.FileStorageService;
import com.redapp.tasker.services.PhotosService;
import com.redapp.tasker.services.StagesService;
import com.redapp.tasker.services.TasksServices;
import com.redapp.tasker.services.UsersService;

@SpringBootApplication
public class TaskerApplication implements CommandLineRunner {
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
	  @Autowired
	  FileStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(TaskerApplication.class, args);
	}
	@Bean
	 public BCryptPasswordEncoder generateBcrypte() {
		 return new BCryptPasswordEncoder();
	 }
	@Override
	public void run(String... args) throws Exception {
		
		  storageService.deleteAll(); storageService.init();
		  this.appRoleRepository.save(new AppRole("MANAGER"));
		  this.appRoleRepository.save(new AppRole("USER")); AppUser
		  user=this.usersService.addUser(new
		  UserRequest("ir.trade88@gmail.com","123456","red company","07098900",
		  "reda@gmail.com","N24","ACTIVE"));
		 

	}   

}
