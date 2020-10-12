package com.redapp.tasker.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
public class PasswordController {
	@Autowired
	private UsersService usersService;

	@GetMapping("/confirmeResetPassword")
	public String confirmeResetPassword(Model model,@RequestParam Long tokenId) {
		
		String resp=this.usersService.confirmeResetPassword(tokenId);
		model.addAttribute("response",resp) ;
		return "tokenResult";
		
	}

}
