package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.Cast;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CastRepository extends MongoRepository<Cast, ObjectId> {
    List<Cast> findByTconst(String tconst);
}
