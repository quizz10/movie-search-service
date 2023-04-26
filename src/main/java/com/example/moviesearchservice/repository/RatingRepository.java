package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.Rating;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, ObjectId> {
    Rating findByTconst(String tconst);
}
