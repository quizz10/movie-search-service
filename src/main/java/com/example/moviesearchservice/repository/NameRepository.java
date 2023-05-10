package com.example.moviesearchservice.repository;

import com.example.moviesearchservice.model.Name;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NameRepository extends MongoRepository<Name, ObjectId> {
    @Query(value = "{'nconst' : ?0, 'primaryProfession' : { $regex: 'act'}}")
    Name findByNconst(String nconst);

}
