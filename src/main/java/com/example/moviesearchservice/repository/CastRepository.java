package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.Cast;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastRepository extends MongoRepository<Cast, ObjectId> {
    List<Cast> findByTconst(String tconst);
}
