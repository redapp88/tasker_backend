package com.redapp.tasker.services;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.redapp.tasker.dao.PhotoRepository;
import com.redapp.tasker.entities.Photo;
@Service
public class PhotosServiceImp implements PhotosService {
@Autowired 
private PhotoRepository photoRepository;
@Autowired
private FileStorageService fileStorageService;
	@Override
	public Photo addPhoto(String url) {
    return this.photoRepository.save(new Photo(url));
	}

	@Override
	public void deletePhoto(Long id) {
	Photo photo=this.getPhoto(id);
	this.photoRepository.deleteById(id);
	//this.fileStorageService.delete(photo.getFilename());
	}

	@Override
	public Photo getPhoto(Long id) {
		 Optional<Photo> opt = this.photoRepository.findById(id);
		 if(opt.isPresent())
			 return opt.get();
		 throw new RuntimeException("Photo introuvable");
	}
	@Override
	 public ResponseEntity download(String fileName) {
		    Photo photo=this.getPhoto(new Long(FilenameUtils.removeExtension(fileName)));
		 	Path path = Paths.get("uploads/"+ fileName);
		 	Resource resource = null;
		 	resource = new ByteArrayResource(photo.getImage());
		 	return ResponseEntity.ok()
		 			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		 			.body(resource);
		 }

	@Override
	public void savePhoto(Photo photo) {
		this.photoRepository.save(photo);
		
	}
	 
	
}
