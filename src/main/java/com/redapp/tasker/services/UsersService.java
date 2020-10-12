package com.redapp.tasker.services;

import java.util.List;

import com.redapp.tasker.entities.AppUser;
import com.redapp.tasker.entities.PasswordRequest;
import com.redapp.tasker.entities.UserRequest;
public interface UsersService {
	public List<AppUser> getUsers(String keyword,String state);
	public List<AppUser> getManagers();
	public AppUser addUser(UserRequest userRequest);
	public AppUser editUser(String username,UserRequest userRequest);
	public AppUser addManager(UserRequest userRequest);
	public AppUser findByUsername(String username);
	public void deleteManager(String username);
	public void deleteUser(String username);
	public void editPassword(String username, PasswordRequest passwordRequest);
	public AppUser getUser(String username);
	public void resetPassword(String username,String password);
	public String confirmeResetPassword(Long id);

}
