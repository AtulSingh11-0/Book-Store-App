package com.demo.bookstoreapp.service;

import com.demo.bookstoreapp.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

  Image uploadImage( MultipartFile image);
  void deleteImage(String publicId);

}
