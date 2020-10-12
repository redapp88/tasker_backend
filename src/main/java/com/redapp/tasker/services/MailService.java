package com.redapp.tasker.services;

import org.springframework.stereotype.Service;

import com.redapp.tasker.entities.Photo;
@Service
public interface MailService {
	public void sendEmail(String body,String title,String to,String from) throws Exception;
}
