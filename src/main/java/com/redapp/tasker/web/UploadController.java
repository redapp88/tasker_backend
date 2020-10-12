package com.redapp.tasker.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.redapp.tasker.entities.Photo;
import com.redapp.tasker.services.FileStorageService;
import com.redapp.tasker.services.PhotosService;

@RestController
public class UploadController {
@Autowired
private PhotosService photosService;

  @Autowired
  FileStorageService storageService;
  @PostMapping("/upload")
  public Photo uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
    Photo photo=this.photosService.addPhoto("");
    storageService.save(file,photo.getId().toString());
    photo.setUrl("files?filename="+photo.getId().toString()+"."+FilenameUtils.getExtension(file.getOriginalFilename()));
    photo.setFilename(photo.getId().toString()+"."+FilenameUtils.getExtension(file.getOriginalFilename()));
    this.photosService.savePhoto(photo);
    return photo;
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      //throw new RunTimeException("error");
      System.out.println(e);
     return null;
    }
  }
  
	@GetMapping(value = "/files")
	public ResponseEntity downloadFile (@RequestParam String filename) throws FileNotFoundException, IOException {
		return this.photosService.download(filename);
	}
  
  
  
}