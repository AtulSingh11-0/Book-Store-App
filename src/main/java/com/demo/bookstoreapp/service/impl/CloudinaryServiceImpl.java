package com.demo.bookstoreapp.service.impl;

import com.cloudinary.Cloudinary;
import com.demo.bookstoreapp.exception.ImageDeletionFailedException;
import com.demo.bookstoreapp.exception.ImageUploadingFailedException;
import com.demo.bookstoreapp.model.Image;
import com.demo.bookstoreapp.service.CloudinaryService;
import com.demo.bookstoreapp.utils.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

@Slf4j
@Service
public class CloudinaryServiceImpl implements CloudinaryService {

  private final Cloudinary cloudinary;

  public CloudinaryServiceImpl ( Cloudinary cloudinary ) {
    this.cloudinary = cloudinary;
  }

  @Override
  public Image uploadImage ( MultipartFile image ) {
    try {
      var result = cloudinary.uploader().upload(image.getBytes(), Map.of(
          "public_id", generateFileName(image.getOriginalFilename()),
          "folder", "bookstoreapp",
          "use_filename", true,
          "unique_filename", true,
          "resource_type", "image"
      ));

      return Image.builder()
          .publicId(result.get("public_id").toString())
          .url(result.get("url").toString())
          .build();

    } catch ( Exception e ) {
      log.error("Error uploading image to cloudinary", e);
      throw new ImageUploadingFailedException(AppConstants.IMAGE_UPLOAD_ERROR);
    }
  }

  @Override
  public void deleteImage ( String publicId ) {
    try {
      cloudinary.uploader().destroy(publicId, Map.of(
          "invalidate", true
      ));
    } catch ( Exception e ) {
      log.error("Error deleting image from cloudinary", e);
      throw new ImageDeletionFailedException(AppConstants.IMAGE_DELETE_ERROR);
    }
  }

  private String generateFileName ( String name) {
    final DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT);
    final String date = dateFormat.format(System.currentTimeMillis());
    return String.format(AppConstants.FILE_NAME_FORMAT, name, date, System.currentTimeMillis());
  }
}
