package com.demo.bookstoreapp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CloudinaryConfig {

  Dotenv dotenv = Dotenv.load();

  private final String CLOUDINARY_URL = dotenv.get("CLOUDINARY_URL");
  private final String CLOUD_NAME = dotenv.get("CLOUD_NAME");
  private final String API_KEY = dotenv.get("API_KEY");
  private final String API_SECRET = dotenv.get("API_SECRET");

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
