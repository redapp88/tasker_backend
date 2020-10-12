package com.redapp.tasker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.redapp.tasker.entities.Task;

public interface TaskRepository extends JpaRepository<Task, String> {
@Query("select t from Task t where t.worker.username like :username")
	public List<Task> findByUser(@Param("username")String username);
@Query("select t from Task t where t.worker.username like :username and t.state in :status and (t.title like :keyword or t.client like :keyword or t.description like :keyword or t.id like :keyword) order by t.creatDate desc")
public List<Task> findByDetails(
		@Param("username")String username,
		@Param("status") List status,
		@Param("keyword")String keyword);

}
