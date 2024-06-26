package com.demo.bookstoreapp.service;

import com.demo.bookstoreapp.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

  public Image uploadImage( MultipartFile image);
  public void deleteImage(String publicId);

}
