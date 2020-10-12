package com.redapp.tasker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redapp.tasker.entities.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
