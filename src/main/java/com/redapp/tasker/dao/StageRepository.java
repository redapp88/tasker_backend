package com.redapp.tasker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.redapp.tasker.entities.Stage;

public interface StageRepository extends JpaRepository<Stage, Long> {
@Query("Select s from Stage s where s.task.id like :taskId order by s.stageDate desc")
	public List<Stage> findByTask(@Param("taskId")String taskId);

}
