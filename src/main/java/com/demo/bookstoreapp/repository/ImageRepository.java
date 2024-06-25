package com.demo.bookstoreapp.repository;

import com.demo.bookstoreapp.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository< Image, String > {
}
