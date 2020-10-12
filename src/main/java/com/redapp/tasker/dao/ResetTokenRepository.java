package com.redapp.tasker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redapp.tasker.entities.ResetToken;

public interface ResetTokenRepository extends JpaRepository<ResetToken,Long > {

}
