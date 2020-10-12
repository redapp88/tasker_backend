package com.redapp.tasker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redapp.tasker.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {

	public AppRole findByRoleName(String roleName);

}
