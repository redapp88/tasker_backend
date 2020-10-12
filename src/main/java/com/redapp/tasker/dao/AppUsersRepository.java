package com.redapp.tasker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.redapp.tasker.entities.AppUser;

public interface AppUsersRepository extends JpaRepository<AppUser, String> {
	
	@Query("select u from AppUser u where (u.name like :keyword or u.username like :keyword or u.mail like :keyword)  and u.appRole.roleName=:role and u.state like :state")
	public List<AppUser> findByDetails(
			@Param(value="keyword") String keyword,
			@Param(value="role") String role,
			@Param(value="state") String state);

}
