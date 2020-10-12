package com.redapp.tasker.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.redapp.tasker.entities.Photo;
@Service
public interface PhotosService {
public Photo addPhoto(String url);
public void deletePhoto(Long id);
public Photo getPhoto(Long id);
public void savePhoto(Photo photo);
ResponseEntity download(String fileName);

}
