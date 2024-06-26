package com.demo.bookstoreapp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CloudinaryConfig {

  private final String CLOUD_NAME = "ddfiyfatr";
  private final String API_KEY = "318361741574497";
  private final String API_SECRET = "EDa1y09Sq_i-bUJDHZFco7CuS2E";

  @Bean
  public Cloudinary cloudinary () {
    /*
    CLOUDINARY_URL=cloudinary://<api_key>:<api_secret>@<cloud_name>
    1. cloud_name: CLOUD_NAME
    2. api_key: API_KEY
    3. api_secret: API_SECRET
    4. secure: true
    5. upload_preset: bookstoreapp
    6. folder: bookstoreapp
    7. resource_type: auto
    */

    return new Cloudinary(ObjectUtils.asMap(
        "cloud_name", CLOUD_NAME,
        "api_key", API_KEY,
        "api_secret", API_SECRET,
        "secure", true,
        "upload_preset", "bookstoreapp",
        "folder", "bookstoreapp",
        "resource_type", "auto"
    ));
  }

}
