package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.Rating;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, ObjectId> {
    Rating findByTconst(String tconst);
}
